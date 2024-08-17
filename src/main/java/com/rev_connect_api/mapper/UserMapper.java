package com.rev_connect_api.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rev_connect_api.dto.UserRegistrationDTO;
import com.rev_connect_api.dto.UserResponseDTO;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", source = "password")
    @Mapping(target = "roles", expression = "java(mapRoles(registrationDTO.getRoles()))")
    @Mapping(target = "userId", ignore = true) // Ignored because it's auto-generated
    @Mapping(target = "createdAt", ignore = true) // Handled by @PrePersist
    @Mapping(target = "updatedAt", ignore = true) // Handled by @PreUpdate
    User toEntity(UserRegistrationDTO registrationDTO);

    @Mapping(target = "fullName", expression = "java(user.getFirstName*() + ' ' + user.getLastName())")
    UserResponseDTO toDTO(User user);

    default Set<Role> mapRoles(Set<String> roles) {
        if(roles == null || roles.isEmpty()) {
            return Set.of(Role.ROLE_USER);
        }
        return roles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }

    //void updateUserFromDTO(UserUpdateDTO dto, @MappingTarget User user);
}