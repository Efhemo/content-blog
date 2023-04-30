package com.efhem.content.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String jwtHeader = request.getHeader("Authorization");

        if (jwtHeader == null || !jwtHeader.contains("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = jwtHeader.substring(7);
        String email = jwtService.extractUsername(jwt);

    }
}
