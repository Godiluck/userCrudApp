package com.example.userCrudApp.controller;

import com.example.userCrudApp.dto.CreateUserDto;
import com.example.userCrudApp.dto.UpdateUserDto;
import com.example.userCrudApp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/new")
    public String createUserForm(Model model) {
        model.addAttribute("user", new CreateUserDto());
        model.addAttribute("isNew", true);
        return "form";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("user") @Valid CreateUserDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew", true);
            return "form";
        }
        userService.save(dto);
        return "redirect:/v1/users";
    }

    @GetMapping("/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findUpdateDtoById(id));
        model.addAttribute("isNew", false);
        model.addAttribute("userId", id);
        return "form";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") @Valid UpdateUserDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew", false);
            model.addAttribute("userId", id);
            return "form";
        }
        userService.update(id, dto);
        return "redirect:/v1/users";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/v1/users";
    }
}
