package com.thiagoalmeida.med9.domain.usecase.user;

import com.thiagoalmeida.med9.domain.entity.User;

import java.util.Optional;

public interface GetUserByIdUseCase {
    Optional<User> execute(Long id);
}
