package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreDbStorage extends BaseDbStorage<Genre> implements GenreStorage {

    private static final String FIND_ALL_QUERY = "SELECT * FROM genres";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM genres WHERE id = ?";

    private static final String FIND_BY_FILM_ID_QUERY = """
            SELECT g.id, g.name
            FROM films  f
            JOIN genre_films gf ON f.id = gf.film_id
            JOIN genres g ON  g.id = gf.genre_id
            WHERE f.id = ?
            ORDER BY g.id
            """;


    public GenreDbStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper, Genre.class);
    }

    @Override
    public Collection<Genre> getAllGenre() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Optional<Genre> getGenreById(int id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

    @Override
    public List<Genre> getGenreByFilmId(int filmId) {
        return findMany(FIND_BY_FILM_ID_QUERY, filmId);
    }
}


