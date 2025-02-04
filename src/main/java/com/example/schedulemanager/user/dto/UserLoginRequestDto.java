package com.example.schedulemanager.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserLoginRequestDto {
    private String id;
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;
}
