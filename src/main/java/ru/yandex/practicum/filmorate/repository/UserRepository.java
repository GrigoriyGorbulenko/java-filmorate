package ru.yandex.practicum.filmorate.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.JDBCType;

@Component
@AllArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public User getById(Integer id) {
        jdbcTemplate.queryForObject();
    }
}
