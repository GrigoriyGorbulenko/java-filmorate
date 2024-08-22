package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;


@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private FilmService filmService;

//    @GetMapping
//    public Collection<Film> getAllFilms() {
//        return filmService.getAllFilms();
//    }
//
//    @GetMapping
//    public Film getFilmById(@RequestBody Film newFilm) {
//        return filmService.getFilmById(newFilm);
//    }
//
//    @PostMapping
//    public Film createFilm(@RequestBody Film newFilm) {
//        return filmService.createFilm(newFilm);
//    }
//
//    @PutMapping
//    public Film update(@RequestBody Film newFilm) {
//        return filmService.updateFilm(newFilm);
//    }
//
//    @DeleteMapping
//    public void deleteFilm(@RequestBody Film newFilm) {
//        filmService.deleteFilm(newFilm);
//    }
}
