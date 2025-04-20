package com.thiagoalmeida.med9.infrastructure.mapper;

import com.thiagoalmeida.med9.application.dto.user.UserResponse;
import com.thiagoalmeida.med9.domain.entity.User;
import com.thiagoalmeida.med9.infrastructure.persistence.entities.UserJpaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper() {
        this.modelMapper = new ModelMapper();
    }

    public <S, T> T map(S source, Class<T> targetClass) {
        if (source == null) return null;
        return modelMapper.map(source, targetClass);
    }

    public User toDomain(UserJpaEntity entity) {
        if (entity == null) return null;
        return new User(
            entity.getId(),
            entity.getUsername(),
            entity.getPassword(),
            entity.getName(),
            entity.getEmail(),
            entity.getRole()
        );
    }

    public UserResponse toUserResponse(User user) {
        if (user == null) return null;
        return new UserResponse(
            user.id(),
            user.username(),
            user.name(),
            user.email(),
            user.role()
        );
    }

    public UserJpaEntity toEntity(User domain) {
        if (domain == null) return null;
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(domain.id());
        entity.setUsername(domain.username());
        entity.setPassword(domain.password());
        entity.setName(domain.name());
        entity.setEmail(domain.email());
        entity.setRole(domain.role());
        return entity;
    }
}