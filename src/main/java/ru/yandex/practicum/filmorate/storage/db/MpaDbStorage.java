package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;


import java.util.Collection;
import java.util.Optional;

@Repository
public class MpaDbStorage extends BaseDbStorage<Mpa> implements MpaStorage {
    private static final String FIND_ALL_QUERY = "SELECT * FROM mpa";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM mpa WHERE id = ?";

    private static final String FIND_BY_FILM_ID_QUERY = """
            SELECT f.mpa_id, m.name
            FROM films f JOIN mpa m ON f.mpa_id = m.id WHERE f.id = ?
            """;

    public MpaDbStorage(JdbcTemplate jdbc, RowMapper<Mpa> mapper) {
        super(jdbc, mapper, Mpa.class);
    }

    @Override
    public Collection<Mpa> getAllMpa() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Optional<Mpa> getMpaById(int id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

    @Override
    public Optional<Mpa> getMpaByFilmId(int filmId) {
        return findOne(FIND_BY_FILM_ID_QUERY, filmId);
    }
}


