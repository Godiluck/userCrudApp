package com.example.userCrudApp.mapper;

import com.example.userCrudApp.dto.UserCreateDto;
import com.example.userCrudApp.dto.UserUpdateRequestDto;
import com.example.userCrudApp.dto.UserUpdateResponseDto;
import com.example.userCrudApp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(UserCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUserFromDto(UserUpdateRequestDto dto, @MappingTarget User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(
            target = "roleIds",
            expression = "java(user.getRoles() != null ? user.getRoles().stream().map(r -> r.getId()).collect(java.util.stream.Collectors.toSet()) : java.util.Collections.emptySet())"
    )
    UserUpdateResponseDto toUpdateResponseDto(User user);
}
