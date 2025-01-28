package com.example.schedulemanager.user.repository;

import com.example.schedulemanager.user.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcTemplateUserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    public JdbcTemplateUserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(String id) {
        return jdbcTemplate.query("SELECT * FROM USER WHERE id = ?", (rs, rowNum) -> new User(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        ), id).stream().findFirst();
    }

    @Override
    public void saveUser(User user) {
        jdbcTemplate.update("INSERT INTO USER (id, name, email, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)",
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getUpdatedAt());

    }

    @Override
    public int deleteUserById(String id) {
        return jdbcTemplate.update("delete from USER where id = ?", id);
    }

}
