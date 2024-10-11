package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {
    Collection<User> getAllUsers();

    User getUserById(Integer userId);

    User createUser(User newUser);

    User updateUser(User newUser);

    void createFriend(Integer userId, Integer friendId);

    void deleteFriend(Integer userId, Integer friendId);

    List<User> getFriendsById(Integer userId);

    List<User> getCommonFriends(Integer userId, Integer friendId);

}
