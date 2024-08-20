package ru.yandex.practicum.filmorate.storage.film;


import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> getAllUsers();

    User getUserById(User newUser);

    User createUser(User newUser);

    void deleteUser(User newUser);

    User updateUser(User newUser);
}
