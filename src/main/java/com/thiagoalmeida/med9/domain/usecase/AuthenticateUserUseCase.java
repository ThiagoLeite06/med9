package com.thiagoalmeida.med9.domain.usecase;

import com.thiagoalmeida.med9.application.dto.AuthRequest;
import com.thiagoalmeida.med9.application.dto.AuthResponse;

public interface AuthenticateUserUseCase {
    AuthResponse execute(AuthRequest authRequest);
}
