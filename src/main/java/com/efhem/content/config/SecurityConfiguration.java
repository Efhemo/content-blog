package com.efhem.content.config;

import com.efhem.content.error.DelegatedAuthenticationEntryPoint;
import com.efhem.content.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.efhem.content.user.Permission.ADMIN_READ;
import static com.efhem.content.user.Permission.MANAGER_READ;
import static com.efhem.content.user.Role.ADMIN;
import static com.efhem.content.user.Role.MANAGER;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    private final TokenService tokenService;
    private final DelegatedAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
        https
                //Disable csrf
                .csrf().disable()
                .authorizeHttpRequests(
                  authorizeRequest ->
                    authorizeRequest
                            //Permit some url that doesn't need authentication
                            .requestMatchers("/api/v1/auth/**").permitAll()

                            //Only admin and manager can access this endpoints
                            .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())

                            .requestMatchers(HttpMethod.GET, "/api/v1/management/**").hasAnyAuthority(
                                    ADMIN_READ.getPermission(), MANAGER_READ.getPermission()
                            )

                             //The rest of the url requires authentication
                            .anyRequest().authenticated()
                )

                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)


                //STATELESS because we are authenticating once per request
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)


                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(tokenService)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())

        ;

        return https.build();
    }
}
