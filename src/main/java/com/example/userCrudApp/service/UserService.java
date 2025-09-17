package com.example.userCrudApp.service;

import com.example.userCrudApp.dto.CreateUserDto;
import com.example.userCrudApp.dto.UpdateUserDto;
import com.example.userCrudApp.exception.UserNotFoundException;
import com.example.userCrudApp.model.User;
import com.example.userCrudApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void save(CreateUserDto dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .nickName(dto.getNickName())
                .build();

        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void update(Long id, UpdateUserDto dto) {
        userRepository.findById(id).ifPresent(existingUser -> {
            existingUser.setName(dto.getName());
            existingUser.setEmail(dto.getEmail());
            existingUser.setAge(dto.getAge());
            existingUser.setNickName(dto.getNickName());
            userRepository.save(existingUser);
        });
    }

    public UpdateUserDto findUpdateDtoById(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    UpdateUserDto dto = new UpdateUserDto();
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    dto.setAge(user.getAge());
                    dto.setNickName(user.getNickName());
                    return dto;
                })
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + id + " не найден"));
    }
}
