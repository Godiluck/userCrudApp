package com.example.userCrudApp.service;

import com.example.userCrudApp.dto.UserCreateDto;
import com.example.userCrudApp.model.User;
import com.example.userCrudApp.repository.RoleRepository;
import com.example.userCrudApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(UserCreateDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username уже существует");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Set.of(roleRepository.findByRole("ROLE_USER")
                        .orElseThrow(() -> new IllegalArgumentException("Роль USER не найдена"))))
                .build();

        userRepository.save(user);
    }
}
