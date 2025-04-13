package com.thiagoalmeida.med9.application.usecase.user;

import com.thiagoalmeida.med9.domain.entity.User;
import com.thiagoalmeida.med9.domain.repository.UserRepository;
import com.thiagoalmeida.med9.domain.usecase.user.CreateUserUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;

    public CreateUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(User user) {
        if (userRepository.existsByUsername(user.username())) {
            throw new IllegalArgumentException("Username já está em uso");
        }
        if (userRepository.existsByEmail(user.email())) {
            throw new IllegalArgumentException("Email já está em uso");
        }

        return userRepository.save(user);
    }
}