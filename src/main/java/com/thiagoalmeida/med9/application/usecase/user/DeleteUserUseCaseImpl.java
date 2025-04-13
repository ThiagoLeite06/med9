package com.thiagoalmeida.med9.application.usecase.user;

import com.thiagoalmeida.med9.domain.repository.UserRepository;
import com.thiagoalmeida.med9.domain.usecase.user.DeleteUserUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserRepository userRepository;

    public DeleteUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }
}
