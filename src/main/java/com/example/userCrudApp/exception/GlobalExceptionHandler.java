package com.example.userCrudApp.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(Model model) {
        model.addAttribute("errorMessage", "У вас нет прав для доступа к этой странице.");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleAnyException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
