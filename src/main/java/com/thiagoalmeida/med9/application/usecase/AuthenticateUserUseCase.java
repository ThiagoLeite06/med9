package com.thiagoalmeida.med9.application.usecase;

import com.thiagoalmeida.med9.application.dto.AuthRequest;
import com.thiagoalmeida.med9.infrastructure.persistence.entities.UserJpaEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.thiagoalmeida.med9.application.dto.LoginResponse;
import com.thiagoalmeida.med9.domain.repository.UserRepository;
import com.thiagoalmeida.med9.infrastructure.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticateUserUseCase {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse execute(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        
//        UserJpaEntity userJpaEntity = userRepository.findByUsername(request.getUsername())
//            .orElseThrow(() -> new RuntimeException("User not found"));
//
//        String token = jwtTokenProvider.generateToken(userJpaEntity);
        
        return new LoginResponse(token);
    }
} 