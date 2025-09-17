package com.example.userCrudApp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserDto {
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @NotNull(message = "Возраст обязателен")
    @Min(value = 18, message = "Пользователь должен быть старше 18 лет")
    private Integer age;

    @NotBlank(message = "Никнейм не может быть пустым")
    private String nickName;
}
