package com.rev_connect_api.dto;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private Boolean isBusiness;
    
}