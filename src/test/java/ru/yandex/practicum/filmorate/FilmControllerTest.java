package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class FilmControllerTest {
    private final FilmController filmController = new FilmController();

    @Test
    public void validationCreateFilmWithoutData() {
        Film film = new Film();
        assertThrows(RuntimeException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void validationCreateFilmWithNullName() {
        Film film = new Film();
        film.setDescription("Очень веселое");
        film.setReleaseDate(LocalDate.of(1980, 2, 22));
        film.setDuration(120);
        assertThrows(RuntimeException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void validationCreateUserFilmBlankName() {
        Film film = new Film();
        film.setName(" ");
        film.setDescription("Очень веселое");
        film.setReleaseDate(LocalDate.of(1980, 2, 22));
        film.setDuration(120);
        assertThrows(RuntimeException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void validationCreateUserFilmWithDescriptionMoreThan200Symbol() {
        Film film = new Film();
        film.setName("Batman");
        film.setDescription("Очень веселоеОчень веселоеОчень веселоеОчень " +
                "веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселое" +
                "Очень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселое" +
                "Очень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселое" +
                "Очень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселое" +
                "Очень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселое" +
                "Очень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселое" +
                "Очень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселое" +
                "Очень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселое" +
                "Очень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселое" +
                "Очень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселоеОчень веселое");
        film.setReleaseDate(LocalDate.of(1980, 2, 22));
        film.setDuration(120);
        assertThrows(RuntimeException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void validationCreateUserFilmWithDateOfRelease1840() {
        Film film = new Film();
        film.setName("Batman");
        film.setDescription("Очень веселое");
        film.setReleaseDate(LocalDate.of(1840, 2, 22));
        film.setDuration(120);
        assertThrows(RuntimeException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void validationCreateUserFilmWithNegativeDuration() {
        Film film = new Film();
        film.setName("Batman");
        film.setDescription("Очень веселое");
        film.setReleaseDate(LocalDate.of(1980, 2, 22));
        film.setDuration(-1);
        assertThrows(RuntimeException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void validationCreateUserFilmWithZeroDuration() {
        Film film = new Film();
        film.setName("Batman");
        film.setDescription("Очень веселое");
        film.setReleaseDate(LocalDate.of(1980, 2, 22));
        film.setDuration(0);
        assertThrows(RuntimeException.class, () -> filmController.createFilm(film));
    }
}

