package com.thiagoalmeida.med9.application.usecase;

import com.thiagoalmeida.med9.application.dto.auth.LoginRequest;
import com.thiagoalmeida.med9.application.dto.auth.LoginResponse;
import com.thiagoalmeida.med9.domain.usecase.LoginUseCase;
import com.thiagoalmeida.med9.infrastructure.persistence.entities.UserJpaEntity;
import com.thiagoalmeida.med9.infrastructure.persistence.repositories.UserJpaRepository;
import com.thiagoalmeida.med9.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserJpaRepository userRepository;

    @Override
    public LoginResponse execute(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserJpaEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        String token = jwtTokenProvider.generateToken(authentication);

        String role = "";
        if (!user.getAuthorities().isEmpty()) {
            GrantedAuthority authority = user.getAuthorities().iterator().next();
            role = authority.getAuthority();
        }

        return new LoginResponse(
                token,
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                role
        );
    }
}
