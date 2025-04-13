package com.thiagoalmeida.med9.application.usecase.user;

import com.thiagoalmeida.med9.domain.entity.User;
import com.thiagoalmeida.med9.domain.repository.UserRepository;
import com.thiagoalmeida.med9.domain.usecase.user.UpdateUserUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;

    public UpdateUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(User user) {

        Optional<User> existingByUsername = userRepository.findByUsername(user.username());
        if (existingByUsername.isPresent() && !existingByUsername.get().id().equals(user.id())) {
            throw new IllegalArgumentException("Username já está em uso por outro usuário");
        }

        Optional<User> existingByEmail = userRepository.findByEmail(user.email());
        if (existingByEmail.isPresent() && !existingByEmail.get().id().equals(user.id())) {
            throw new IllegalArgumentException("Email já está em uso por outro usuário");
        }

        return userRepository.save(user);
    }
}
