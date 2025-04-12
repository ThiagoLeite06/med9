package com.thiagoalmeida.med9.application.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thiagoalmeida.med9.application.dto.CreateUserRequest;
import com.thiagoalmeida.med9.domain.model.User;
import com.thiagoalmeida.med9.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public User execute(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        
        return userRepository.save(user);
    }
} 