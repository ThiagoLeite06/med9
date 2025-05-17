package com.thiagoalmeida.med9.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // Endpoints públicos
                .requestMatchers("/api/public/**").permitAll()
                // Endpoints de administração
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                // Endpoints de usuários
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                // Endpoints de médicos
                .requestMatchers("/api/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                // Endpoints de enfermeiros
                .requestMatchers("/api/nurse/**").hasAnyRole("NURSE", "ADMIN")
                // Endpoints de pacientes
                .requestMatchers("/api/patient/**").hasAnyRole("PATIENT", "ADMIN")
                // Endpoints de consultas
                .requestMatchers("/api/appointments").hasAnyRole("DOCTOR", "NURSE", "ADMIN")
                .requestMatchers("/api/appointments/my").hasRole("PATIENT")
                .requestMatchers("/api/appointments/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN")
                // Endpoints de histórico
                .requestMatchers("/api/medical-history/my").hasRole("PATIENT")
                .requestMatchers("/api/medical-history/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usando NoOpPasswordEncoder temporariamente para testes
        return NoOpPasswordEncoder.getInstance();
    }
}