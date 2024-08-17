package com.rev_connect_api.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.rev_connect_api.dto.UserRegistrationDTO;
import com.rev_connect_api.dto.UserResponseDTO;
import com.rev_connect_api.dto.UserUpdateDTO;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // map UserRegistrationDTO to User entity
    @Mapping(target = "password", source = "password")
    @Mapping(target = "roles", expression = "java(mapRoles(registrationDTO.getRoles()))")
    @Mapping(target = "grantedAuthorities", ignore = true)
    @Mapping(target = "userId", ignore = true) // Ignored because it's auto-generated
    @Mapping(target = "createdAt", ignore = true) // Handled by @PrePersist
    @Mapping(target = "updatedAt", ignore = true) // Handled by @PreUpdate
    User toEntity(UserRegistrationDTO registrationDTO);

    // map User entity to UserResponseeDTO
    @Mapping(target = "fullName", expression = "java(user.getFirstName*() + ' ' + user.getLastName())")
    UserResponseDTO toDTO(User user);

    // map fields from UserUpdateDTO to existing User entity
    @Mapping(target = "password", source = "password")
    @Mapping(target = "roles", expression = "java(mapRoles(dto.getRoles()))")
    @Mapping(target = "grantedAuthorities", ignore = true)
    @Mapping(target = "userId", ignore = true) // Ignored because it's auto-generated
    @Mapping(target = "createdAt", ignore = true) // Handled by @PrePersist
    @Mapping(target = "updatedAt", ignore = true) // Handled by @PreUpdate
    void updateUserFromDTO(UserUpdateDTO dto, @MappingTarget User user);
    
    // concert a Set of role strings to a Set of Role enums
    // this method helps in mapping roles from DTOs to the entity
    default Set<Role> mapRoles(Set<String> roles) {
        if(roles == null || roles.isEmpty()) {
            return Set.of(Role.ROLE_USER); // default role ROLE_USER if no roles are provided
        }
        return roles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }

    //void updateUserFromDTO(UserUpdateDTO dto, @MappingTarget User user);
}