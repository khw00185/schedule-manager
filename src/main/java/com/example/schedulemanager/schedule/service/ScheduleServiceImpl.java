package com.example.schedulemanager.schedule.service;

import com.example.schedulemanager.common.dto.ResponseDto;
import com.example.schedulemanager.schedule.dto.request.AllSchedulesRequestDto;
import com.example.schedulemanager.schedule.dto.request.ScheduleRequestDto;
import com.example.schedulemanager.schedule.dto.response.PagingResponseDto;
import com.example.schedulemanager.schedule.dto.response.ScheduleResponseDto;
import com.example.schedulemanager.schedule.entity.Schedule;
import com.example.schedulemanager.schedule.exception.ScheduleNotFoundException;
import com.example.schedulemanager.schedule.exception.SchedulePermissionDeniedException;
import com.example.schedulemanager.schedule.repository.ScheduleRepository;
import com.example.schedulemanager.user.dto.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    @Override
    public ResponseDto<PagingResponseDto> getAllSchedules(AllSchedulesRequestDto dto) {
        PagingResponseDto response = scheduleRepository.getAllSchedules(dto, dto.getPageable());
        return ResponseDto.success(response);

    }

    @Override
    public ResponseDto<ScheduleResponseDto> getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);
        return ResponseDto.success(new ScheduleResponseDto(schedule));
    }



    @Override
    public ResponseDto<ScheduleResponseDto> createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(
                null,
                scheduleRequestDto.getTodo(),
                getCurrentUserId(),
                null,
                null
        );

        schedule = scheduleRepository.saveSchedule(schedule);

        return ResponseDto.success(new ScheduleResponseDto(schedule));
    }

    @Override
    public ResponseDto<ScheduleResponseDto> updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {

        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);

        if (!schedule.getAuthorId().equals(getCurrentUserId())) {
            throw new SchedulePermissionDeniedException();
        }

        schedule.setTodo(scheduleRequestDto.getTodo());

        int updatedRow = scheduleRepository.updateSchedule(schedule);
        if(updatedRow == 0){
            throw new ScheduleNotFoundException();
        }
        schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);
        return ResponseDto.success(new ScheduleResponseDto(schedule));
    }

    @Override
    public ResponseDto<String> deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);

        if (!schedule.getAuthorId().equals(getCurrentUserId())) {
            throw new SchedulePermissionDeniedException();
        }


        int deletedRow = scheduleRepository.deleteScheduleById(id);
        if(deletedRow == 0){
            throw new ScheduleNotFoundException();
        }
        return ResponseDto.success("일정이 성공적으로 삭제되었습니다.");
    }

    private String getCurrentUserId() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
    }
}
