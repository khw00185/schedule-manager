package com.example.schedulemanager.user.dto;

import lombok.Getter;
@Getter
public class UserRequestDto {
    private String id;
    private String userName;
    private String email; //email유효성 검사 해야함
    private String password;

}
