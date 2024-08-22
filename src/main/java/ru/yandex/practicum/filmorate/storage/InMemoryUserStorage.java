package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();

    private int idGenerator = 1;

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public User getUserById(User newUser) {
        return users.get(newUser.getId());
    }

    @Override
    public User createUser(User newUser) {
        newUser.setId(idGenerator++);
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public void deleteUser(User newUser) {
        users.remove(newUser.getId());
    }

    @Override
    public User updateUser(User newUser) {
        User oldUser = users.get(newUser.getId());
        oldUser.setName(newUser.getName());
        oldUser.setBirthday(newUser.getBirthday());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setLogin(newUser.getLogin());
        return oldUser;
    }
}
