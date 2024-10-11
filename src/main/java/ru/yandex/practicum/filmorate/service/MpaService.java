package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class MpaService {
    private MpaStorage mpaStorage;

    public Collection<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    public Mpa getMpaById(Integer mpaId) {
        log.info("Пришел запрос на получение рейтинга с id {} ", mpaId);
        return mpaStorage.getMpaById(mpaId)
                .orElseThrow(() -> new NotFoundException("Рейтинг с id = " + mpaId + " не найден"));
    }
}
