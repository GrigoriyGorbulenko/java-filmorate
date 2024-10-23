package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.*;



@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;
    private final MpaStorage mpaStorage;
    private static final int MAX_LENGTH_DESCRIPTION = 200;

    private static final LocalDate DATE_RELEASE_NOT_EARLY = LocalDate.of(1895, 12, 28);

    public Collection<Film> getAllFilms() {

        return filmStorage.getAllFilms()
                .stream()
                .map(this::mapToFilm)
                .toList();
    }

    public Film getFilmById(Integer filmId) {
        Film film = filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм с id = " + filmId + " не найден"));
        return mapToFilm(film);
    }

    public Film createFilm(Film newFilm) {
        log.trace("Пришел запрос на добавление фильма {}", newFilm);
        validate(newFilm);
        mpaStorage.getMpaById(newFilm.getMpa().getId())
                .orElseThrow(() -> new ValidationException(" Mpa не найден"));
        List<Genre> genreList = newFilm.getGenres();
        for (Genre genre : genreList) {
            genreStorage.getGenreById(genre.getId())
                    .orElseThrow(() -> new ValidationException("Жанр не найден"));
        }

        return mapToFilm(filmStorage.createFilm(newFilm));
    }


    public Film updateFilm(Film newFilm) {
        log.info("Пришел запрос на обновление фильма с id {}", newFilm.getId());
        validate(newFilm);
        filmStorage.getFilmById(newFilm.getId())
                .orElseThrow(() -> new NotFoundException("Фильм не найден"));
        Film updateFilm = filmStorage.updateFilm(newFilm);

        log.trace("Обновлен фильм с id {}", newFilm.getId());
        return mapToFilm(updateFilm);
    }

    public void createLike(Integer filmId, Integer userId) {
        filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм с id = " + filmId + " не найден"));
        userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));
        log.info("Пришел запрос на добавление лайка фильму с id {}", userId);

        filmStorage.createLike(filmId, userId);

        log.info("Добавлен лайка фильму с id {}", userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм с id = " + filmId + " не найден"));
        userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));
        log.info("Пришел запрос на даление лайка фильму с id {}", userId);

        filmStorage.deleteLike(filmId, userId);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        Collection<Film> list = new ArrayList<>();
        filmStorage.getPopularFilms(count).forEach(film -> {
            Film newFilm = mapToFilm(film);
            list.add(newFilm);
        });
        return list;

    }

    public Collection<Film> getPopularFilms2(Integer count) {
        Collection<Film> list = new ArrayList<>();
        filmStorage.getPopularFilms(count).forEach(film -> {
            Film newFilm = mapToFilm(film);
            list.add(newFilm);
        });
        return list;

    }

    private void validate(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.debug("Пользователь не ввел название фильма");
            throw new ValidationException("Название не должно быть пустым");
        }
        if (film.getDescription().length() > MAX_LENGTH_DESCRIPTION) {
            log.debug("Пользователь ввел описание фильма больше 200 символов");
            throw new ValidationException("Длина описания не должна превышать 200 символов");
        }
        if (film.getReleaseDate().isBefore(DATE_RELEASE_NOT_EARLY)) {
            log.debug("Пользователь ввел некорректную дату фильма");
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            log.debug("Пользователь ввел некорректную продолжительность фильма");
            throw new ValidationException("Продолжительность фильма должна быть больше 0");
        }
    }

    private Film mapToFilm(Film film) {
        Mpa mpa = mpaStorage.getMpaByFilmId(film.getId())
                .orElseThrow(() -> new NotFoundException(" Mpa не найден"));
        film.setMpa(mpa);
        List<Genre> genres = film.getGenres();
        Set<Genre> genreList = new HashSet<>(genres);

        for (Genre genre : genreList) {
            genreStorage.getGenreById(genre.getId())
                    .orElseThrow(() -> new ValidationException("Жанр не найден"));
            filmStorage.createGenre(genre.getId(), film.getId());
        }
        film.setGenres((genreStorage.getGenreByFilmId(film.getId())));

        return film;
    }
}
