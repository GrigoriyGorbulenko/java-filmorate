package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.storage.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.storage.mappers.UserRowMapper;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@JdbcTest
@AutoConfigureTestDatabase
@Import({FilmDbStorage.class, FilmRowMapper.class,
        GenreDbStorageTest.class, GenreRowMapper.class,
        UserDbStorage.class, UserRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {

    private final FilmStorage filmStorage;
    private final GenreDbStorage genreDbStorage;
    private final UserStorage userStorage;

    @Test
    void testFindAllFilms() {
        assertEquals(5,  filmStorage.getAllFilms().size(), "Неверное  пользователей");
    }

    @Test
    void testFindFilmById() {

        Optional<Film> filmOptional = filmStorage.getFilmById(1);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "Гладиатор")
                );
    }

    @Test
    void testCreateFilm() {
        Film film = filmStorage.createFilm(getTestFilm2());
        assertThat(film).
                hasFieldOrPropertyWithValue("id", 6);
    }

    @Test
    void testUpdateUser() {
        assertThat(filmStorage.updateFilm(getTestFilm()))
                .hasFieldOrPropertyWithValue("name", "Унесенные ветром")
                .hasFieldOrPropertyWithValue("description", "Топ")
                .hasFieldOrPropertyWithValue("duration", 130);
    }

    @Test
    void testCreateGenre() {
        filmStorage.createGenre(2, 3);
        List<Genre> genre = genreDbStorage.getGenreByFilmId(3);
        assertThat(genre.getFirst()).hasFieldOrPropertyWithValue("name", "Драма");
    }

    @Test
    void testCreateLike() {
        filmStorage.createLike(2, 1);
        filmStorage.createLike(2, 2);
        int likes = userStorage.getLikesById(2).size();
        assertEquals(2, likes, "Количество лайков не соответствует");
    }

    @Test
    void testDeleteLike() {
        filmStorage.createLike(2, 1);
        filmStorage.deleteLike(2, 3);
        int likes = userStorage.getLikesById(2).size();
        assertEquals(1, likes, "Количество лайков не соответствует");
    }


    @Test
    void testFindPopularFilms() {

         List<Film> popularFilm = filmStorage.getPopularFilms(3).stream().toList();
        int likes = userStorage.getLikesById(popularFilm.getFirst().getId()).size();
         assertEquals(3, popularFilm.size(), "Количество фильмов не соответствует");
         assertEquals(3, likes, "Количество лайков не соответствует");
    }


    private static Film getTestFilm() {
        Film film = new Film();
        film.setId(3);
        film.setName("Унесенные ветром");
        film.setDescription("Топ");
        film.setReleaseDate(LocalDate.of(2000, 5, 1));
        film.setDuration(130);

        return film;
    }

    private static Film getTestFilm2() {
        Mpa mpa = new Mpa();
        mpa.setId(1);
        Film film = new Film();
        film.setName("Гладиатор4");
        film.setDescription("Исторический4");
        film.setReleaseDate(LocalDate.of(2000, 3, 1));
        film.setDuration(120);
        film.setMpa(mpa);

        return film;
    }
}