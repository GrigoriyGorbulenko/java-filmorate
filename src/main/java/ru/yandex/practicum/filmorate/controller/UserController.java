package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

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

    @DeleteMapping
    public void deleteUser(@RequestBody Integer userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void createFriend(@PathVariable("id") int userId,
                             @PathVariable("friends") int friendId) {
        userService.createFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") int userId,
                             @PathVariable("friends") int friendId) {
        userService.deleteFriend(userId, friendId);
    }
}

