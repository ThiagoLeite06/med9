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
        return modelMapper.map(entity, User.class);
    }

    public UserResponse toUserResponse(User user) {
        if (user == null) return null;
        return modelMapper.map(user, UserResponse.class);
    }

    public UserJpaEntity toEntity(User domain) {
        if (domain == null) return null;
        return modelMapper.map(domain, UserJpaEntity.class);
    }
}