package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;
    private final MpaStorage mpaStorage;
    private final int maxLengthDescription = 200;
    private final LocalDate dateReleaseNotEarle = LocalDate.of(1895, 12, 28);

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(Integer filmId) {
        if (filmStorage.getFilmById(filmId) == null) {
            validate(filmStorage.getFilmById(filmId));
            Film film = filmStorage.getFilmById(filmId);
            film.setMpa(mpaStorage.getMpaByFilmId(filmId)
                    .orElseThrow(() -> new NotFoundException("Фильм с данным mpa не найден")));
            return film;
        }
        log.debug("Фильм с введеным id = {} не найден ", filmId);
        throw new NotFoundException("Фильм с id = " + filmId + " не найден");
    }

    public Film createFilm(Film newFilm) {
        log.trace("Добавлен фильм {}", newFilm);
        validate(newFilm);
        Film film = filmStorage.getFilmById(newFilm.getId());
        film.setMpa(mpaStorage.getMpaByFilmId(newFilm.getId())
                .orElseThrow(() -> new NotFoundException("Фильм с данным mpa не найден")));
        return film;
    }


    public Film updateFilm(Film newFilm) {
        log.info("Пришел запрос на обновление фильма с id {}", newFilm.getId());
        if (newFilm.getId() == null) {
            log.info("Пользователь не указал id фильма");
            throw new NotFoundException("Id должен быть указан");
        }
        if (filmStorage.getFilmById(newFilm.getId()) != null) {
            validate(newFilm);
            log.trace("Обновлен фильм с id {}", newFilm.getId());
            return filmStorage.updateFilm(newFilm);
        }
        log.debug("Фильм с введеным id = {} не найден ", newFilm.getId());
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    public void createLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        log.info("Пришел запрос на добавление лайка фильму с id {}", userId);
        if (film == null) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден");
        }
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        film.getUsersLikes().add(userId);
        log.info("Добавлен лайка фильму с id {}", userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        log.info("Пришел запрос на даление лайка фильму с id {}", userId);
        if (film == null) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден");
        }
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        film.getUsersLikes().remove(userId);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.getAllFilms().stream()
                .sorted(Comparator.comparing(film -> film.getUsersLikes().size(), Comparator.reverseOrder()))
                .limit(count)
                .toList();
    }

    private void validate(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.debug("Пользователь не ввел название фильма");
            throw new ValidationException("Название не должно быть пустым");
        }
        if (film.getDescription().length() > maxLengthDescription) {
            log.debug("Пользователь ввел описание фильма больше 200 символов");
            throw new ValidationException("Длина описания не должна превышать 200 символов");
        }
        if (film.getReleaseDate().isBefore(dateReleaseNotEarle)) {
            log.debug("Пользователь ввел некорректную дату фильма");
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            log.debug("Пользователь ввел некорректную продолжительность фильма");
            throw new ValidationException("Продолжительность фильма должна быть больше 0");
        }
    }
}
