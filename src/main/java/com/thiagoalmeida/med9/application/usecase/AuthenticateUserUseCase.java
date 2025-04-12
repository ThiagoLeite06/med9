package com.thiagoalmeida.med9.application.usecase;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.thiagoalmeida.med9.application.dto.AuthRequest;
import com.thiagoalmeida.med9.application.dto.AuthResponse;
import com.thiagoalmeida.med9.infrastructure.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticateUserUseCase {
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    
    public AuthResponse execute(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        String token = jwtTokenProvider.generateToken(authentication);
        String username = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        
        return new AuthResponse(token, username, role);
    }
} 