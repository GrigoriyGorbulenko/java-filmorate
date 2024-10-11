package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

     Collection<Film> getAllFilms();

     Film getFilmById(Integer filmId);

     Film createFilm(Film newFilm);

     Film updateFilm(Film newFilm);

     void createLike(Integer filmId, Integer userId);

     void deleteLike(Integer filmId, Integer userId);

     Collection<Film> getPopularFilms(Integer count);

}
