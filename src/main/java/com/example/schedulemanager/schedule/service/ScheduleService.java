package com.example.schedulemanager.schedule.service;

import com.example.schedulemanager.schedule.dto.ScheduleRequestDto;
import com.example.schedulemanager.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    List<ScheduleResponseDto> getAllSchedules();
    ScheduleResponseDto getScheduleById(Long id);
    ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto);
    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto);
    void deleteSchedule(Long id);
}
