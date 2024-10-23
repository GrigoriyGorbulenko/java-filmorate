package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final FilmDbStorage filmDbStorage;

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }


    public User getUserById(Integer userId) {

        return userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));
    }

    public User createUser(User newUser) {
        log.info("Пришел запрос на добавление пользователя с логином {} ", newUser.getLogin());
        validate(newUser);
        log.trace("Добавлен пользователь {} ", newUser);
        return userStorage.createUser(newUser);
    }


    public User updateUser(User newUser) {
        log.info("Пришел запрос на обновление пользователя с логином {} ", newUser.getLogin());
        User user = userStorage.getUserById(newUser.getId())
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден"));
        validate(newUser);

        return userStorage.updateUser(newUser);
    }

    public void createFriend(Integer userId, Integer friendId) {
        log.info("Пришел запрос на добавление друга с id = {} ", friendId);
        userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));

        userStorage.getUserById(friendId)
                .orElseThrow(() -> new NotFoundException("Друг с id = " + friendId + " не найден"));

        userStorage.createFriend(userId, friendId);

    }

    public void deleteFriend(Integer userId, Integer friendId) {
        log.info("Пришел запрос на удаление друга с id {}",
                friendId);
        userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));

        userStorage.getUserById(friendId)
                .orElseThrow(() -> new NotFoundException("Друг с id = " + friendId + " не найден"));

        userStorage.deleteFriend(userId, friendId);
    }

    public List<User> getFriendsById(Integer userId) {
        userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));

        return userStorage.getFriendsById(userId);
    }

    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));

        userStorage.getUserById(friendId)
                .orElseThrow(() -> new NotFoundException("Друг с id = " + friendId + " не найден"));

        return userStorage.getCommonFriends(userId, friendId);
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


    public List<Film> getUsersRecommendations(Integer userId) {
        Collection<Film> recommendUserFilms = filmDbStorage.getUsersRecommendations(userId);
        log.info("Нашел список фильмов для рекомендации");
        Collection<Film> userFilms = filmDbStorage.getFilmsLikesByUser(userId);
        log.info("Получил список фильмов пользователя для рекомендации {}", userId);
        recommendUserFilms.removeAll(userFilms);
        List<Film> recommendFilms = new ArrayList<>();
        for (Film indexFilm : recommendUserFilms) {
            recommendFilms.add(filmDbStorage.getFilmById(indexFilm.getId())
                    .orElseThrow(() -> new NotFoundException("Фильм с id = " + indexFilm + " не найден")));
        }
        return recommendFilms;
    }
}
