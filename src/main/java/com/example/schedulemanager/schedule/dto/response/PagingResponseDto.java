package com.example.schedulemanager.schedule.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PagingResponseDto {
    private List<ScheduleResponseDto> schedules;
    private int totalCount;
    private int totalPages;
    private int currentPage;

    public PagingResponseDto(List<ScheduleResponseDto> schedules, int totalCount, int totalPages, int currentPage) {
        this.schedules = schedules;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }
}
