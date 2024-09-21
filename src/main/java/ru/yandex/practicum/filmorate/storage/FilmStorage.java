package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FilmStorage {

     Collection<Film> getAllFilms();

     Film getFilmById(Integer filmId);

     Film createFilm(Film newFilm);

     void deleteFilm(Integer filmId);

     Film updateFilm(Film newFilm);

     void addLike(Film film, User user);

     void deleteLike(Long filmId, Long userId);
}
