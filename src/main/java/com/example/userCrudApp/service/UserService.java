package com.example.userCrudApp.service;

import com.example.userCrudApp.dto.UserCreateDto;
import com.example.userCrudApp.dto.UserUpdateRequestDto;
import com.example.userCrudApp.dto.UserUpdateResponseDto;
import com.example.userCrudApp.exception.UserNotFoundException;
import com.example.userCrudApp.mapper.UserMapper;
import com.example.userCrudApp.model.Role;
import com.example.userCrudApp.model.User;
import com.example.userCrudApp.repository.RoleRepository;
import com.example.userCrudApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public void save(UserCreateDto dto) {
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Set<Role> roles = new HashSet<>();
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            roles.addAll(roleRepository.findAllById(dto.getRoleIds()));
        } else {
            roles.add(roleRepository.findByRole("ROLE_USER")
                    .orElseThrow(() -> new IllegalArgumentException("Роль USER не найдена")));
        }
        user.setRoles(roles);

        userRepository.save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Пользователь с id " + id + "не найден");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, UserUpdateRequestDto dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + id + "не найден"));

        userMapper.updateUserFromDto(dto, existingUser);

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRole("ROLE_USER")
                .orElseThrow(() -> new IllegalArgumentException("Роль USER не найдена")));
        if (!dto.getRoleIds().isEmpty()) {
            roles.addAll(roleRepository.findAllById(dto.getRoleIds()));
        }
        existingUser.setRoles(roles);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(existingUser);
    }

    public UserUpdateResponseDto findUpdateDtoById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUpdateResponseDto)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + id + " не найден"));
    }

    public boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public void createRoleIfNotExist(String roleName) {
        roleRepository.findByRole(roleName)
                .orElseGet(() -> roleRepository.save(Role.builder().role(roleName).build()));
    }

    @Transactional
    public void createAdmin(UserCreateDto dto) {
        Role adminRole = roleRepository.findByRole("ROLE_ADMIN")
                .orElseThrow(() -> new IllegalArgumentException("Роль ADMIN не найдена"));

        Role userRole = roleRepository.findByRole("ROLE_USER")
                .orElseThrow(() -> new IllegalArgumentException("Роль USER не найдена"));

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of(adminRole, userRole));

        userRepository.save(user);
    }

    public boolean isAdmin(User user) {
        return user.getRoles().stream()
                .anyMatch(r -> "ROLE_ADMIN".equals(r.getRole()));
    }
}
