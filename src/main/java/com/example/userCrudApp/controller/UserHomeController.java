package com.example.userCrudApp.controller;

import com.example.userCrudApp.model.User;
import com.example.userCrudApp.security.UserPrincipal;
import com.example.userCrudApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserHomeController {
    private final UserService userService;

    @GetMapping
    public String userHome(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        User user = userPrincipal.getUser();
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", userService.isAdmin(user));
        return "home";
    }
}
