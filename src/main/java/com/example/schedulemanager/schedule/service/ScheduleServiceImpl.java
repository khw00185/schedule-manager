package com.example.schedulemanager.schedule.service;

import com.example.schedulemanager.schedule.dto.ScheduleRequestDto;
import com.example.schedulemanager.schedule.dto.ScheduleResponseDto;
import com.example.schedulemanager.schedule.entity.Schedule;
import com.example.schedulemanager.schedule.repository.ScheduleRepository;
import com.example.schedulemanager.user.dto.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    @Override
    public List<ScheduleResponseDto> getAllSchedules() {
        List<Schedule> scheduleList = scheduleRepository.getAllSchedules();
        List<ScheduleResponseDto> scheduleResponseDtoList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            scheduleResponseDtoList.add(
                    new ScheduleResponseDto(schedule)
            );
        }
        return scheduleResponseDtoList;
    }

    @Override
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }



    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(
                null,
                scheduleRequestDto.getTodo(),
                getCurrentUserId(),
                null,
                null
        );

        schedule = scheduleRepository.saveSchedule(schedule);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {

        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);

        if (!schedule.getAuthorId().equals(getCurrentUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        }

        schedule.setTodo(scheduleRequestDto.getTodo());

        int updatedRow = scheduleRepository.updateSchedule(schedule);
        if(updatedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
        schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);

        if (!schedule.getAuthorId().equals(getCurrentUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
        }


        int deletedRow = scheduleRepository.deleteScheduleById(id);
        if(deletedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }

    private String getCurrentUserId() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
    }
}
