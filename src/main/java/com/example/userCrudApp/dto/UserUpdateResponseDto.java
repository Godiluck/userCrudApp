package com.example.userCrudApp.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserUpdateResponseDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String username;
    private String password;
    private Set<Long> roleIds;
}
