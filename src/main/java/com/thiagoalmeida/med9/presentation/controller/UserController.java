package com.thiagoalmeida.med9.presentation.controller;

import com.thiagoalmeida.med9.application.dto.user.UserRequest;
import com.thiagoalmeida.med9.application.dto.user.UserResponse;
import com.thiagoalmeida.med9.domain.entity.User;
import com.thiagoalmeida.med9.domain.usecase.user.*;
import com.thiagoalmeida.med9.infrastructure.mapper.UserMapper;
import com.thiagoalmeida.med9.infrastructure.persistence.entities.UserJpaEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UserMapper userMapper;

    public UserController(CreateUserUseCase createUserUseCase,
                          GetUserByIdUseCase getUserByIdUseCase,
                          GetAllUsersUseCase getAllUsersUseCase,
                          UpdateUserUseCase updateUserUseCase,
                          DeleteUserUseCase deleteUserUseCase,
                          UserMapper userMapper) {
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        User user = new User(
            null, // id será gerado pelo banco
            request.username(),
            request.password(),
            request.name(),
            request.email(),
            request.role()
        );
        User created = createUserUseCase.execute(user);
        return new ResponseEntity<>(userMapper.toUserResponse(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return getUserByIdUseCase.execute(id)
                .map(user -> ResponseEntity.ok(userMapper.toUserResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = getAllUsersUseCase.execute();
        List<UserResponse> responses = users.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest request) {
        User user = new User(
            id,
            request.username(),
            request.password(),
            request.name(),
            request.email(),
            request.role()
        );
        User updated = updateUserUseCase.execute(user);
        return ResponseEntity.ok(userMapper.toUserResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}