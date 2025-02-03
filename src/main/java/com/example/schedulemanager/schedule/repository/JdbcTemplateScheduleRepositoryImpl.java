package com.example.schedulemanager.schedule.repository;

import com.example.schedulemanager.schedule.dto.request.AllSchedulesRequestDto;
import com.example.schedulemanager.schedule.dto.response.PagingResponseDto;
import com.example.schedulemanager.schedule.dto.response.ScheduleResponseDto;
import com.example.schedulemanager.schedule.entity.Schedule;
import com.example.schedulemanager.schedule.exception.ScheduleNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JdbcTemplateScheduleRepositoryImpl implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public PagingResponseDto getAllSchedules(AllSchedulesRequestDto dto, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM schedule WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (dto.getModifiedDateStart() != null && !dto.getModifiedDateStart().isEmpty() && dto.getModifiedDateEnd() != null && !dto.getModifiedDateEnd().isEmpty()) {
            sql.append(" AND updated_at BETWEEN ? AND ?");
            countSql.append(" AND updated_at BETWEEN ? AND ?");

            params.add(LocalDate.parse(dto.getModifiedDateStart()).atStartOfDay());
            params.add(LocalDate.parse(dto.getModifiedDateEnd()).atTime(23, 59, 59));
        }

        if (dto.getAuthorId() != null && !dto.getAuthorId().isEmpty()) {
            sql.append(" AND author_id = ?");
            countSql.append(" AND author_id = ?");
            params.add(dto.getAuthorId());
        }

        Object[] paramArrayCount = params.toArray();
        int totalCount = jdbcTemplate.queryForObject(countSql.toString(), Integer.class, paramArrayCount);

        // 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / pageable.getPageSize());

        sql.append(" ORDER BY updated_at DESC");
        sql.append(" LIMIT ? OFFSET ?");

        params.add(pageable.getPageSize());
        params.add(pageable.getOffset());


        Object[] paramArray = params.toArray();
        List<Schedule> scheduleList = jdbcTemplate.query(sql.toString(), scheduleRowMapper(), paramArray);

        // 결과를 DTO로 변환
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleList.stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());


        return new PagingResponseDto(scheduleResponseDtoList, totalCount, totalPages, dto.getPage());
    }


    @Override
    public Integer authorExists(String authorId) {

        Integer count = jdbcTemplate.queryForObject("select 1 from schedule where author_id = ? LIMIT 1", Integer.class, authorId);
        if (count == null || count == 0) {
            return 0;
        }
        return count;
    }

    @Override
    public Schedule getScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapper(), id);
        return result.stream().findFirst().orElseThrow(ScheduleNotFoundException::new);
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
