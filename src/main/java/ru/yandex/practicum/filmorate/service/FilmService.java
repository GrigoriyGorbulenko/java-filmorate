package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final int maxLengthDescription = 200;
    private final LocalDate dateReleaseNotEarle = LocalDate.of(1895, 12, 28);

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(Film newFilm) {
        if (filmStorage.getAllFilms().contains(newFilm)) {
            validate(newFilm);
            return filmStorage.getFilmById(newFilm);
        }
        log.debug("Фильм с введеным id = {} не найден ", newFilm.getId());
        throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    public Film createFilm(Film newFilm) {
        log.trace("Добавлен фильм {}", newFilm);
        return filmStorage.createFilm(newFilm);
    }

    public void deleteFilm(Film newFilm) {
        filmStorage.deleteFilm(newFilm);
        log.trace("Удален фильм {}", newFilm);
    }

    public Film updateFilm(Film newFilm) {
        log.info("Пришел запрос на обновление фильма с id {}", newFilm.getId());
        if (newFilm.getId() == null) {
            log.info("Пользователь не указал id фильма");
            throw new ValidationException("Id должен быть указан");
        }
        if (filmStorage.getAllFilms().contains(newFilm)) {
            validate(newFilm);
            log.trace("Обновлен фильм с id {}", newFilm.getId());
            return filmStorage.createFilm(newFilm);
        }
        log.debug("Фильм с введеным id = {} не найден ", newFilm.getId());
        throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден");
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
