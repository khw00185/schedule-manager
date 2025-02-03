package com.example.schedulemanager.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
@Getter
public class UserRequestDto {
    @NotBlank(message = "ID는 필수 입력값입니다.")
    private String id;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String userName;

    @NotBlank
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email; //email유효성 검사 해야함

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상으로 설정해주세요.")
    private String password;

}
