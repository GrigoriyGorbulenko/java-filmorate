package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;


@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable ("id") int filmId) {
        return filmService.getFilmById(filmId);
    }

    @PostMapping
    public Film createFilm(@RequestBody Film newFilm) {
        return filmService.createFilm(newFilm);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film newFilm) {
        return filmService.updateFilm(newFilm);
    }


    @PutMapping("/{id}/like/{userId}")
    public void createLike(@PathVariable("id") int filmId,
                           @PathVariable("userId") int userId) {
         filmService.createLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") int filmId,
                           @PathVariable("userId") int userId) {
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@PathVariable("count")  @RequestParam(defaultValue = "10") int count) {
       return filmService.getPopularFilms(count);
    }
}
