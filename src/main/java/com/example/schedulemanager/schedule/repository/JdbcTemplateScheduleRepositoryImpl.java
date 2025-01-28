package com.example.schedulemanager.schedule.repository;

import com.example.schedulemanager.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepositoryImpl implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return jdbcTemplate.query("select * from schedule", scheduleRowMapper());
    }

    @Override
    public Schedule getScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapper(), id);
        return result.stream().findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id= "+id));
    }

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        LocalDateTime now = LocalDateTime.now();
        if (schedule.getCreateTime() == null) {
            schedule.setCreateTime(now);
        }
        schedule.setUpdateTime(now);


        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("Schedule")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", schedule.getTodo());
        parameters.put("author_Id", schedule.getAuthorId());
        parameters.put("created_at", schedule.getCreateTime());
        parameters.put("updated_at", schedule.getUpdateTime());

        Number key = jdbcInsert.executeAndReturnKey(parameters);

        return new Schedule(
                key.longValue(),
                schedule.getTodo(),
                schedule.getAuthorId(),
                schedule.getCreateTime(),
                schedule.getUpdateTime()
        );
    }

    @Override
    public int updateSchedule(Schedule schedule) {
        LocalDateTime now = LocalDateTime.now();
        schedule.setUpdateTime(now);
        return jdbcTemplate.update("update schedule set todo = ?, updated_at =? where id = ?", schedule.getTodo(), schedule.getUpdateTime(), schedule.getId());
    }

    @Override
    public int deleteScheduleById(Long id) {
        return jdbcTemplate.update("delete from schedule where id = ?", id);
    }


    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("todo"),
                rs.getString("author_id"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }

}
