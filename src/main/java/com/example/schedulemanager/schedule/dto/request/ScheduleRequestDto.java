package com.example.schedulemanager.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    @NotBlank
    @Size(min = 1, max = 200, message = "일정은 200자 이내로 작성 가능합니다.")
    private String todo;

}
