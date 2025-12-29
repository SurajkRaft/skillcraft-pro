package com.skillcraft.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless API (common for JWT)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions, JWT based
            )
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**").permitAll() // Allow public access to auth endpoints
                .anyRequest().authenticated() // All other endpoints require authentication
            );
            // TODO: In the next step, we will add a JWT Authentication Filter here.

        return http.build();
    }
}