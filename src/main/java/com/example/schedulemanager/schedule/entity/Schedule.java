package com.example.schedulemanager.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String todo;
    private String authorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
