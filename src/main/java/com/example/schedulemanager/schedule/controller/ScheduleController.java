package com.example.schedulemanager.schedule.controller;

import com.example.schedulemanager.common.dto.ResponseDto;
import com.example.schedulemanager.schedule.dto.request.AllSchedulesRequestDto;
import com.example.schedulemanager.schedule.dto.request.ScheduleRequestDto;
import com.example.schedulemanager.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<ResponseDto<?>> getAllSchedules(
            @RequestParam(required = false) String modifiedDateStart,
            @RequestParam(required = false) String modifiedDateEnd,
            @RequestParam(required = false) String authorId,
            @RequestParam int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        AllSchedulesRequestDto dto = new AllSchedulesRequestDto(modifiedDateStart, modifiedDateEnd, authorId, page, size);

        return ResponseEntity.ok(scheduleService.getAllSchedules(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<?>> createSchedule(@Valid @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, scheduleRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(id));
    }
}
