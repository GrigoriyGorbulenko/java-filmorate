package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }

    @PutMapping
    public User updateUser(@RequestBody User newUser) {
        return userService.updateUser(newUser);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void createFriend(@PathVariable("id") int userId,
                             @PathVariable("friendId") int friendId) {
        userService.createFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") int userId,
                             @PathVariable("friendId") int friendId) {
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendsById(@PathVariable("id") int userId) {
       return userService.getFriendsById(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") int userId,
                            @PathVariable("otherId") int friendId) {
        return userService.getCommonFriends(userId, friendId);
    }
}

