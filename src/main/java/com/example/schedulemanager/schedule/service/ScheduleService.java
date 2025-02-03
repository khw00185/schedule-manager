package com.example.schedulemanager.schedule.service;

import com.example.schedulemanager.common.dto.ResponseDto;
import com.example.schedulemanager.schedule.dto.request.AllSchedulesRequestDto;
import com.example.schedulemanager.schedule.dto.request.ScheduleRequestDto;
import com.example.schedulemanager.schedule.dto.response.PagingResponseDto;
import com.example.schedulemanager.schedule.dto.response.ScheduleResponseDto;

public interface ScheduleService {
    ResponseDto<PagingResponseDto> getAllSchedules(AllSchedulesRequestDto dto); // 수정
    ResponseDto<ScheduleResponseDto> getScheduleById(Long id); // 수정
    ResponseDto<ScheduleResponseDto> createSchedule(ScheduleRequestDto scheduleRequestDto); // 수정
    ResponseDto<ScheduleResponseDto> updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto); // 수정
    ResponseDto<String> deleteSchedule(Long id); // 수정
}
