package com.thiagoalmeida.med9.application.usecase.user;

import com.thiagoalmeida.med9.domain.entity.User;
import com.thiagoalmeida.med9.domain.repository.UserRepository;
import com.thiagoalmeida.med9.domain.usecase.user.GetAllUsersUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllUsersUseCaseImpl implements GetAllUsersUseCase {
    private final UserRepository userRepository;

    public GetAllUsersUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> execute() {
        return userRepository.findAll();
    }
}
