package com.efhem.content.auth;

import com.efhem.content.auth.model.AuthenticationResponse;
import com.efhem.content.auth.model.SignInRequest;
import com.efhem.content.auth.model.SignupRequest;
import com.efhem.content.config.JWTService;
import com.efhem.content.token.TokenEntity;
import com.efhem.content.token.TokenRepository;
import com.efhem.content.token.TokenType;
import com.efhem.content.user.Role;
import com.efhem.content.user.User;
import com.efhem.content.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    private final AuthenticationManager authManager;

    public AuthenticationResponse signup(SignupRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .isEnabled(true)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .createdAt(new Date())
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        revokeExistingTokens(user);
        saveToken(user, jwtToken);

        return new AuthenticationResponse( jwtToken );
    }

    private void revokeExistingTokens(User user){
        //revoke all token
        var existingTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if(existingTokens.isEmpty()){
            return;
        }
        existingTokens.forEach(tokenEntity -> {
            tokenEntity.setExpired(true);
            tokenEntity.setRevoked(true);
        });
        tokenRepository.saveAll(existingTokens);
    }

    private void saveToken(User user, String jwtToken){
        var token = TokenEntity.builder()
                .tokenType(TokenType.BEARER)
                .token(jwtToken)
                .user(user)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    public AuthenticationResponse signIn(SignInRequest request) {

        var user  = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var authentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        //throws AuthenticationException if authentication is not successful
        authManager.authenticate(authentication);

        var jwtToken = jwtService.generateToken(user);

        revokeExistingTokens(user);
        saveToken(user, jwtToken);

        return new AuthenticationResponse( jwtToken );
    }
}
