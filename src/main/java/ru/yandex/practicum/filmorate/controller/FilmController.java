package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();

    private int idGenerator = 1;
    private final int maxLengthDescription = 200;
    private final LocalDate dateReleaseNotEarle = LocalDate.of(1895, 12, 28);

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film createFilm(@RequestBody Film newFilm) {
        log.info("Пришел запрос на добавление фильма с названием {}", newFilm.getName());
        validation(newFilm);
        newFilm.setId(idGenerator++);
        films.put(newFilm.getId(), newFilm);
        log.trace("Добавлен фильм {}",newFilm);
        return newFilm;
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        log.info("Пришел запрос на обновление фильма с id {}", newFilm.getId());
        if (newFilm.getId() == null) {
            log.info("Пользователь не указал id фильма");
            throw new ValidationException("Id должен быть указан");
        }
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());
            validation(newFilm);
            oldFilm.setName(newFilm.getName());
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            oldFilm.setDuration(newFilm.getDuration());
            log.trace("Обновлен фильм с id {}", oldFilm.getId());
            return oldFilm;
        }
        log.debug("Фильм с введеным id = {} не найден ", newFilm.getId());
        throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    private void validation(Film film) {
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
