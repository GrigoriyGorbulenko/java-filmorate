package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

     Collection<Film> getAllFilms();

     Film getFilmById(Integer filmId);

     Film createFilm(Film newFilm);

     void deleteFilm(Integer filmId);

     Film updateFilm(Film newFilm);
}
