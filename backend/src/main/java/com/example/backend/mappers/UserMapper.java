package com.example.backend.mappers;

import com.example.backend.dto.UserJwtDto;
import com.example.backend.dto.UserRegisterDto;
import com.example.backend.dto.UserResponseDto;
import com.example.backend.entities.User;

public class UserMapper {

    public static UserJwtDto toUserJwtDto(User user, String token) {
        return UserJwtDto.builder()
                .description(user.getDescription())
                .address(user.getAddress())
                .roles(user.getRoles())
                .birthDate(user.getBirthDate())
                .username(user.getUsername())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .id(user.getId())
                .token(token)
                .build();
    }

    public static UserResponseDto toResponsetDto(User user) {
        return UserResponseDto.builder()
                .description(user.getDescription())
                .address(user.getAddress())
                .roles(user.getRoles())
                .birthDate(user.getBirthDate())
                .username(user.getUsername())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .id(user.getId())
                .build();
    }

    public static User toEntity(UserRegisterDto userDto) {

        return User.builder()
                .description(userDto.getDescription())
                .address(userDto.getAddress())
                .roles(userDto.getRoles())
                .birthDate((userDto.getBirthDate()))
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .lastName(userDto.getLastName())
                .firstName(userDto.getFirstName())
                .build();
    }
}
