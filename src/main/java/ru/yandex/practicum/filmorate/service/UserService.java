package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.UserStorage;

import java.time.LocalDate;
import java.util.Collection;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }


    public User getUserById(User newUser) {
        if (userStorage.getAllUsers().contains(newUser)) {
            validate(newUser);
            return userStorage.getUserById(newUser);
        }
        log.debug("Пользователь с введеным id = {} не найден", newUser.getId());
        throw new ValidationException("Пользователь с id = " + newUser.getId() + " не найден");
    }


    public User createUser(User newUser) {
        log.info("Пришел запрос на добавление пользователя с логином {} ", newUser.getLogin());
        validate(newUser);
        log.trace("Добавлен пользователь {} ",newUser);
        return userStorage.createUser(newUser);
    }


    public void deleteUser(User newUser) {
        userStorage.deleteUser(newUser);
    }


    public User updateUser(User newUser) {
        log.info("Пришел запрос на обновление пользователя с логином {} ", newUser.getLogin());
        if (newUser.getId() == null) {
            log.info("Пользователь не указал id");
            throw new ValidationException("Id должен быть указан");
        }
        if (userStorage.getAllUsers().contains(newUser)) {
            validate(newUser);
            log.trace("Обновлен пользователь с id {} ", newUser.getId());
            return userStorage.updateUser(newUser);
        }
        log.debug("Пользователь с введеным id = {} не найден", newUser.getId());
        throw new ValidationException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    private void validate(User newUser) {
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
