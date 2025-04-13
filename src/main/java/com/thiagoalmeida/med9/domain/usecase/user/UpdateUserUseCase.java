package com.thiagoalmeida.med9.domain.usecase.user;

import com.thiagoalmeida.med9.domain.entity.User;

public interface UpdateUserUseCase {
    User execute(User user);
}
