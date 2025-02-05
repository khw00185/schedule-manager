package com.example.schedulemanager.user.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {
    private String id;
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;
}
