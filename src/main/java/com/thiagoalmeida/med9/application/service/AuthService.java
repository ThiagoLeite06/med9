package com.thiagoalmeida.med9.application.service;

import com.thiagoalmeida.med9.infrastructure.persistence.entities.UserJpaEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thiagoalmeida.med9.application.dto.LoginResponse;
import com.thiagoalmeida.med9.application.dto.RegisterRequest;
import com.thiagoalmeida.med9.domain.repository.UserRepository;
import com.thiagoalmeida.med9.infrastructure.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        
        UserJpaEntity userJpaEntity = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        String token = jwtTokenProvider.generateToken(userJpaEntity);
        
        return new LoginResponse(token);
    }

    public UserJpaEntity register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        UserJpaEntity userJpaEntity = new UserJpaEntity();
        userJpaEntity.setUsername(request.getUsername());
        userJpaEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userJpaEntity.setRole(request.getRole());

        return userRepository.save(userJpaEntity);
    }
} 