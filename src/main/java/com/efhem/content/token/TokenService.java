package com.efhem.content.token;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String jwtHeader = request.getHeader("Authorization");

        if (jwtHeader == null || !jwtHeader.contains("Bearer ")) {
            return;
        }

        String jwtToken = jwtHeader.substring(7);

        var token = tokenRepository.findByToken(jwtToken).orElse(null);
        if(token != null){
            token.setRevoked(true);
            token.setExpired(true);
            tokenRepository.save(token);
        }
    }
}
