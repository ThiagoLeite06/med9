package com.thiagoalmeida.med9.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiagoalmeida.med9.application.dto.AuthRequest;
import com.thiagoalmeida.med9.application.dto.AuthResponse;
import com.thiagoalmeida.med9.application.usecase.AuthenticateUserUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthenticateUserUseCase authenticateUserUseCase;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authenticateUserUseCase.execute(request);
        return ResponseEntity.ok(response);
    }
} 