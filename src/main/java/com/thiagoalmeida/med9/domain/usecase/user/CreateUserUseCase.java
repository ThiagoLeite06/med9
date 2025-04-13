package com.thiagoalmeida.med9.domain.usecase.user;

import com.thiagoalmeida.med9.domain.entity.User;

public interface CreateUserUseCase {
    User execute(User user);
}
