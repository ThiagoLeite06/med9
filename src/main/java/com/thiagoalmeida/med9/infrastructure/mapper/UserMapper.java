package com.thiagoalmeida.med9.infrastructure.mapper;

import com.thiagoalmeida.med9.domain.entity.User;
import com.thiagoalmeida.med9.domain.enums.Role;
import com.thiagoalmeida.med9.infrastructure.persistence.entities.RoleJpaEntity;
import com.thiagoalmeida.med9.infrastructure.persistence.entities.UserJpaEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper() {
        this.modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Role.class, RoleJpaEntity.class);
        modelMapper.createTypeMap(RoleJpaEntity.class, Role.class);

        modelMapper.addMappings(new PropertyMap<User, UserJpaEntity>() {
            @Override
            protected void configure() {
                // Exemplo: map().setUserEmail(source.email());
                // Use apenas se os nomes das propriedades forem diferentes
            }
        });
    }

    public User toDomain(UserJpaEntity entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, User.class);
    }

    public UserJpaEntity toEntity(User domain) {
        if (domain == null) return null;
        return modelMapper.map(domain, UserJpaEntity.class);
    }
}