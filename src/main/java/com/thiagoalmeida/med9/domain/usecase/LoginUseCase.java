package com.thiagoalmeida.med9.domain.usecase;

import com.thiagoalmeida.med9.application.dto.auth.LoginRequest;
import com.thiagoalmeida.med9.application.dto.auth.LoginResponse;

public interface LoginUseCase {
    LoginResponse execute(LoginRequest loginRequest);
}
