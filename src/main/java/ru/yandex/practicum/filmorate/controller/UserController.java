package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();

    private int idGenerator = 1;

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser) {
        log.info("Пришел запрос на добавление пользователя с логином {} ", newUser.getLogin());
        validation(newUser);
        newUser.setId(idGenerator++);
        users.put(newUser.getId(), newUser);
        log.trace("Добавлен пользователь {} ",newUser);
        return newUser;
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
        log.info("Пришел запрос на обновление пользователя с логином {} ", newUser.getLogin());
        if (newUser.getId() == null) {
            log.info("Пользователь не указал id");
            throw new ValidationException("Id должен быть указан");
        }
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            validation(newUser);
            oldUser.setName(newUser.getName());
            oldUser.setBirthday(newUser.getBirthday());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setLogin(newUser.getLogin());
            log.trace("Обновлен пользователь с id {} ", oldUser.getId());
            return oldUser;
        }
        log.debug("Пользователь с введеным id = {} не найден", newUser.getId());
        throw new ValidationException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    private void validation(User newUser) {
        if (newUser.getName() == null || newUser.getName().isBlank()) {
            newUser.setName(newUser.getLogin());
            log.trace("Имя пользователя не указано, подставили значение логина {} ", newUser.getLogin());
        }
        if (newUser.getEmail() == null || newUser.getEmail().isBlank() ||
                newUser.getEmail().split("@").length != 2) {
            log.debug("Пользователь некорректно ввел почту {} ", newUser.getEmail());
            throw new ValidationException("Пользователь некорректно ввел почту");
        }
        if (newUser.getLogin() == null || newUser.getLogin().isBlank() ||
                newUser.getLogin().split(" ").length > 1) {
            log.debug("Пользователь некорректно ввел данные логина {} ", newUser.getLogin());
            throw new ValidationException("Пользователь некорректно ввел даанные логина");
        }
        if (!newUser.getBirthday().isBefore(LocalDate.now())) {
            log.debug("Пользователь указал некорректную дату рождения {} ", newUser.getBirthday());
            throw new ValidationException("Пользователь указал некорректную дату рождения");
        }
    }
}

