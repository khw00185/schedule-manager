package com.example.schedulemanager.user.service;

import com.example.schedulemanager.common.dto.ResponseDto;
import com.example.schedulemanager.user.dto.UserLoginRequestDto;
import com.example.schedulemanager.user.dto.UserRequestDto;
import com.example.schedulemanager.user.dto.UserResponseDto;

public interface UserService {
    ResponseDto<UserResponseDto> register(UserRequestDto dto);

    ResponseDto<UserResponseDto> login(UserLoginRequestDto dto);

    ResponseDto<String> deleteUserById();
}
