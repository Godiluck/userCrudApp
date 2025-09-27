package com.example.userCrudApp.config;

import com.example.userCrudApp.dto.UserCreateDto;
import com.example.userCrudApp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class JavaConfig {

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public CommandLineRunner initRolesAndAdmin(UserService userService) {
        return args -> {
            userService.createRoleIfNotExist("ROLE_ADMIN");
            userService.createRoleIfNotExist("ROLE_USER");

            if (!userService.isUsernameExist("admin")) {
                userService.createAdmin(new UserCreateDto("Admin", "admin@admin.com", 30, "admin", "admin"));
            }
        };
    }
}
