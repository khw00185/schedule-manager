package com.example.schedulemanager.schedule.repository;

import com.example.schedulemanager.schedule.dto.request.AllSchedulesRequestDto;
import com.example.schedulemanager.schedule.dto.response.PagingResponseDto;
import com.example.schedulemanager.schedule.entity.Schedule;
import org.springframework.data.domain.Pageable;

public interface ScheduleRepository {
    PagingResponseDto getAllSchedules(AllSchedulesRequestDto dto, Pageable pageable);
    Integer authorExists(String authorId);
    Schedule getScheduleByIdOrElseThrow(Long id);
    Schedule saveSchedule(Schedule schedule);
    int updateSchedule(Schedule schedule);
    int deleteScheduleById(Long id);
}
