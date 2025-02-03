package com.example.schedulemanager.schedule.dto.response;

import com.example.schedulemanager.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String todo;
    private String authorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.todo = schedule.getTodo();
        this.authorId = schedule.getAuthorId();
        this.createTime = schedule.getCreateTime();
        this.updateTime = schedule.getUpdateTime();
    }

}
