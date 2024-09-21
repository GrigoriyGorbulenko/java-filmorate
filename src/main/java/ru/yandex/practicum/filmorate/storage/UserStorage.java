package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> getAllUsers();

    User getUserById(Integer userId);

    User createUser(User newUser);

    void deleteUser(Integer userId);

    User updateUser(User newUser);

    void addFriend(User user, User friend);

    void deleteFriend(Integer userId, Integer friendId);
}
