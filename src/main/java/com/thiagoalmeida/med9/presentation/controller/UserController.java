package com.thiagoalmeida.med9.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiagoalmeida.med9.application.dto.CreateUserRequest;
import com.thiagoalmeida.med9.application.usecase.CreateUserUseCase;
import com.thiagoalmeida.med9.domain.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final CreateUserUseCase createUserUseCase;
    
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = createUserUseCase.execute(request);
        return ResponseEntity.ok(user);
    }
} 