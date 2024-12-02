package com.example.mapper;

import com.example.dto.user.UserDto;
import com.example.dto.user.UserReq;
import com.example.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public User mapTo(UserReq req ) {
        return User.builder().username(req.getUsername()).email(req.getEmail()).phone(req.getPhone()).lastName(req.getLastName()).firstName(req.getFirstName()).role("USER").password(passwordEncoder.encode(req.getPassword())).build();
    }
    public UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .fullName(user.fullName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .role(user.getRole())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .createdAt(user.getCreatedAtUTC())
                .updatedAt(user.getUpdatedAtUTC())
                .build();
    }
}
