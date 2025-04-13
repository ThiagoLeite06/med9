package com.thiagoalmeida.med9.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        User user = toUserDomain(request, null);
        User created = userUseCase.createUser(user);
        return new ResponseEntity<>(toUserResponse(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userUseCase.getUserById(id)
                .map(user -> ResponseEntity.ok(toUserResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userUseCase.getAllUsers().stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest request) {
        User user = toUserDomain(request, id);
        User updated = userUseCase.updateUser(user);
        return ResponseEntity.ok(toUserResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private User toUserDomain(UserRequest request, Long id) {
        return new User(
                id,
                request.username(),
                request.name(),
                request.email(),
                request.password(),
                request.role(),
                request.address()
        );
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.id(),
                user.username(),
                user.name(),
                user.email(),
                user.role(),
                user.address()
        );
    }
}