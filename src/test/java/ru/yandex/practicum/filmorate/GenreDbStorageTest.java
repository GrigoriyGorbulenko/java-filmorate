package ru.yandex.practicum.filmorate;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.mappers.GenreRowMapper;


import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@Import({GenreDbStorage.class, GenreRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDbStorageTest {

    private final GenreStorage genreStorage;

    @Test
    public void testFindAllGenre() {
        Collection<Genre> mpaList = genreStorage.getAllGenre();
        assertEquals(6, mpaList.size(), "Неверно количество жанров");
    }

    @Test
    public void testFindGenreById() {
        Optional<Genre> genreOptional = genreStorage.getGenreById(1);

        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genre ->
                        assertThat(genre).hasFieldOrPropertyWithValue("name", "Комедия")
                );
    }

    @Test
    public void testFindGenreByFilmId() {
        Collection<Genre> genre = genreStorage.getGenreByFilmId(1);
        assertEquals(2, genre.size(), "Количество жанров не совпадает");
    }

}
