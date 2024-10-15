package ru.yandex.practicum.filmorate;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.db.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.mappers.MpaRowMapper;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@Import({MpaDbStorage.class, MpaRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)

public class MpaDbStorageTest {

    private final MpaStorage mpaStorage;


    @Test
    public void testFindAllMpa() {
        Collection<Mpa> mpaList = mpaStorage.getAllMpa();
        assertEquals(5, mpaList.size(), "Неверно количество мpa");
    }

    @Test
    public void testFindMpaById() {
        Optional<Mpa> mpaOptional = mpaStorage.getMpaById(1);

        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa ->
                        assertThat(mpa).hasFieldOrPropertyWithValue("name", "G")
                );
    }

    @Test
    public void testFindMpaByFilmId() {
        Optional<Mpa> mpaOptional = mpaStorage.getMpaByFilmId(1);

        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa ->
                        assertThat(mpa).hasFieldOrPropertyWithValue("name", "PG-13")
                );
    }
}






