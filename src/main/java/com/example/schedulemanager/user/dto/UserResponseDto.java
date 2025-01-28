package com.example.schedulemanager.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String id;
    private String userName;
    private String email;
    private String token;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime updateTime;
}
