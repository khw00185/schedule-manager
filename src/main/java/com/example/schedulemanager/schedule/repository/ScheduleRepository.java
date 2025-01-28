package com.example.schedulemanager.schedule.repository;

import com.example.schedulemanager.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    List<Schedule> getAllSchedules();
    Schedule getScheduleByIdOrElseThrow(Long id);
    Schedule saveSchedule(Schedule schedule);
    int updateSchedule(Schedule schedule);
    int deleteScheduleById(Long id);
}
