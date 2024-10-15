package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;

@Service
@Slf4j
@AllArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public Collection<Genre> getAllGenres() {
        return genreStorage.getAllGenre();
    }

    public Genre getGenreById(Integer genreId) {
        log.info("Пришел запрос на получение жанра с id {} ", genreId);
        return genreStorage.getGenreById(genreId)
                .orElseThrow(() -> new NotFoundException("Жанр с id = " + genreId + " не найден"));
    }
}
