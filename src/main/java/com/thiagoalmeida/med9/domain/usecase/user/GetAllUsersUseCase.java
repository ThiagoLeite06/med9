package com.thiagoalmeida.med9.domain.usecase.user;

import com.thiagoalmeida.med9.domain.entity.User;

import java.util.List;

public interface GetAllUsersUseCase {
    List<User> execute();
}
