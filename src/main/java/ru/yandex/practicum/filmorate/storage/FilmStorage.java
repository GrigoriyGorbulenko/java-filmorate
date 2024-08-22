package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

     Collection<Film> getAllFilms();

     Film getFilmById(Film newFilm);

     Film createFilm(Film newFilm);

     void deleteFilm(Film newFilm);

     Film updateFilm(Film newFilm);
}
