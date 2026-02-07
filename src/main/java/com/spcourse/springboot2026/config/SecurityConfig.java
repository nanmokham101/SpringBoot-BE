package com.spcourse.springboot2026.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/students/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/students/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/students/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/students/**").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/teachers/**").hasAnyRole("ADMIN", "HEADMASTER")
                                .requestMatchers(HttpMethod.PUT, "/teachers/**").hasAnyRole("ADMIN", "HEADMASTER")
                                .requestMatchers(HttpMethod.DELETE, "/teachers/**").hasAnyRole("ADMIN", "HEADMASTER")
                                .requestMatchers(HttpMethod.GET, "/teachers/**").hasAnyRole("ADMIN", "HEADMASTER")

                                .requestMatchers(HttpMethod.POST, "/courses/**").hasAnyRole("ADMIN", "LECTURER")
                                .requestMatchers(HttpMethod.PUT, "/courses/**").hasAnyRole("ADMIN", "LECTURER")
                                .requestMatchers(HttpMethod.DELETE, "/courses/**").hasAnyRole("ADMIN", "LECTURER")
                                .requestMatchers(HttpMethod.GET, "/courses/**").hasAnyRole("ADMIN", "LECTURER")

                                .anyRequest().authenticated()
                )
                // 🔥 VERY IMPORTANT
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

