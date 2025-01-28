package com.example.schedulemanager.user.service;

import com.example.schedulemanager.user.dto.UserLoginRequestDto;
import com.example.schedulemanager.user.dto.UserRequestDto;
import com.example.schedulemanager.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRequestDto dto);

    UserResponseDto login(UserLoginRequestDto dto);

    void deleteUserById();
}
