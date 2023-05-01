package com.efhem.content.auth;

import com.efhem.content.config.JWTService;
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
                .role(Role.USER)
                .createdAt(new Date())
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse( jwtToken );
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
        return new AuthenticationResponse( jwtToken );
    }
}
