package com.thiagoalmeida.med9.application.usecase.user;

import com.thiagoalmeida.med9.domain.entity.User;
import com.thiagoalmeida.med9.domain.repository.UserRepository;
import com.thiagoalmeida.med9.domain.usecase.user.GetUserByIdUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {
    private final UserRepository userRepository;

    public GetUserByIdUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> execute(Long id) {
        return userRepository.findById(id);
    }
}
