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

    private static final String FIND_ALL_FILM_QUERY = "SELECT f.* FROM films f";

    private static final String FIND_FILM_BY_ID_QUERY = "SELECT f.* FROM films f WHERE f.id = ?";

    private static final String INSERT_FILM_QUERY = """
            INSERT INTO films(name, description, release_date, duration, mpa_id)
            VALUES (?, ?, ?, ?, ?)
            """;
    private static final String UPDATE_FILM_QUERY = """
            UPDATE films SET name = ?, description = ?, release_date = ?,duration = ?
            WHERE id = ?
            """;
    private static final String INSERT_GENRE_QUERY = """
            INSERT INTO genre_films (genre_id, film_id) VALUES (?, ?)
            """;

    private static final String INSERT_LIKE_QUERY = """
            INSERT INTO likes (film_id, user_id) VALUES (?, ?)
            """;

    private static final String DELETE_LIKE_QUERY = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";


    private static final String FIND_POPULAR_FILMS_QUERY = """
            SELECT f.* FROM films  f
            LEFT JOIN likes l ON f.id = l.film_id
            GROUP BY  f.id
            ORDER BY count(l.user_id) DESC
            LIMIT  ?
            """;

    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper, Film.class);
    }

    @Override
    public Collection<Film> getAllFilms() {
        return findMany(FIND_ALL_FILM_QUERY);
    }

    @Override
    public Optional<Film> getFilmById(Integer filmId) {

        return findOne(FIND_FILM_BY_ID_QUERY, filmId);
    }

    @Override
    public Film createFilm(Film newFilm) {
        Integer id = insert(
                INSERT_FILM_QUERY,
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
        update(UPDATE_FILM_QUERY,
                newFilm.getName(),
                newFilm.getDescription(),
                newFilm.getReleaseDate(),
                newFilm.getDuration(),
                newFilm.getId()
        );
        return newFilm;
    }

    @Override
    public void createGenre(Integer genreId, Integer filmId) {
        update(
                INSERT_GENRE_QUERY,
                genreId,
                filmId
        );
    }

    @Override
    public void createLike(Integer filmId, Integer userId) {
        update(
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
        return findMany(FIND_POPULAR_FILMS_QUERY, count);
    }

}