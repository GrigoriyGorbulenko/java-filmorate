package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;

@Repository
public class FilmDbStorage extends BaseDbStorage<Film> implements FilmStorage {

    private static final String FIND_ALL_QUERY = "SELECT * FROM films";

    private static final String FIND_FILM_BY_ID_QUERY = "SELECT * FROM films WHERE id = ?";

    private static final String INSERT_QUERY = """
            INSERT INTO films(name, description, release_date, duration)
            VALUES (?, ?, ?, ?) returning id
            """;
    private static final String UPDATE_QUERY = """
            UPDATE films SET name = ?, description = ?, releaseDate = ?,duration = ?
            WHERE id = ?
            """;

    private static final String INSERT_LIKE_QUERY = """
            INSERT INTO likes (film_id, user_id) VALUES (?, ?) returning user_id
            """;

    private static final String DELETE_LIKE_QUERY = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";

    private static final String FIND_POPULAR_FILMS = """
            f.* FROM films f
            LEFT JOIN likes l ON f.id = l.film_id
            GROUP BY  f.id
            ORDER BY count(l.user_id) DESC
            LIMIT ?
            """;

    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper, Film.class);
    }

    @Override
    public Collection<Film> getAllFilms() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Film getFilmById(Integer filmId) {
        Optional<Film> film = findOne(FIND_FILM_BY_ID_QUERY, filmId);
        return film.orElse(null);
    }

    @Override
    public Film createFilm(Film newFilm) {
        Integer id = insert(
                INSERT_QUERY,
                newFilm.getName(),
                newFilm.getDescription(),
                Date.valueOf(newFilm.getReleaseDate()),
                newFilm.getDuration(),
                newFilm.getMpa().getId()
        );
        newFilm.setId(id);
        return newFilm;
    }

    @Override
    public Film updateFilm(Film newFilm) {
        update(UPDATE_QUERY,
                newFilm.getName(),
                newFilm.getDescription(),
                newFilm.getReleaseDate(),
                newFilm.getDuration(),
                newFilm.getMpa().getId(),
                newFilm.getId()
    );
        return newFilm;
    }

    @Override
    public void createLike(Integer filmId, Integer userId) {
        insert(
                INSERT_LIKE_QUERY,
                filmId,
                userId
        );
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        delete(DELETE_LIKE_QUERY, filmId, userId);
    }

    @Override
    public Collection<Film> getPopularFilms(Integer count) {
        return findMany(FIND_POPULAR_FILMS, count);
    }

}
