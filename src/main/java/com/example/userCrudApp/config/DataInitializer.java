package com.example.userCrudApp.config;

import com.example.userCrudApp.dto.UserCreateDto;
import com.example.userCrudApp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initRolesAndAdmin(UserService userService) {
        return args -> {
            userService.createRoleIfNotExist("ROLE_ADMIN");
            userService.createRoleIfNotExist("ROLE_USER");

            if (!userService.isUsernameExist("admin")) {
                userService.createAdmin(UserCreateDto.builder()
                        .name("admin")
                        .email("admin@admin.com")
                        .age(30)
                        .username("admin")
                        .password("admin")
                        .build()
                );
            }
        };
    }
}
