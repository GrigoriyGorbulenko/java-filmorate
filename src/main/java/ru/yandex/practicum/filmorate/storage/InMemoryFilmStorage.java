package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int idGenerator = 1;

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @Override
    public Film getFilmById(Film newFilm) {
        return films.get(newFilm.getId());
    }

    @Override
    public Film createFilm(Film newFilm) {
        newFilm.setId(idGenerator++);
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @Override
    public void deleteFilm(Film newFilm) {
        films.remove(newFilm.getId());
    }

    @Override
    public Film updateFilm(Film newFilm) {
            Film oldFilm = films.get(newFilm.getId());
            oldFilm.setName(newFilm.getName());
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            oldFilm.setDuration(newFilm.getDuration());
            return oldFilm;
    }
}
