package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component

public class UserDbStorage extends BaseDbStorage<User> implements UserStorage {

    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";

    private static final String UPDATE_QUERY = "UPDATE users SET login = ?, name = ?, email = ?, birthday = ? " +
            "WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO users (email, name, login, birthday)" +
            "VALUES (?,?,?,?)";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String FIND_FRIENDS_ID = "SELECT friends_id FROM user_friends WHERE user_id = ?";

    private static final String INSERT_FRIEND_QUERY = "INSERT INTO user_friends (user_id, friend_id) VALUES (?, ?)";
    private static final String FIND_FRIENDS_BY_ID = "SELECT friend_id, email, login, USER_NAME, BIRTHDAY " +
            "FROM user_friend INNER JOIN users ON user_friends.friend_id = user.id WHERE user_friends.user_id = ?";
    private static final String DELETE_FRIEND_QUERY = "DELETE FROM user_friends WHERE user_id = ? AND friend_id = ?";

    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper, User.class);
    }

    @Override
    public Collection<User> getAllUsers() {
        List<User> users = findMany(FIND_ALL_QUERY);
        users.forEach(user -> user.setFriends(getFriendsById(user.getId())));
        return users;
    }

    @Override
    public User getUserById(Integer userId) {
        Optional<User> user = findOne(FIND_BY_ID_QUERY, userId);
        return user.orElse(null);
    }

    @Override
    public User createUser(User newUser) {
        Integer id = insert(INSERT_QUERY,
                newUser.getEmail(),
                newUser.getName(),
                newUser.getLogin(),
                newUser.getBirthday()
        );
        newUser.setId(id);
        return newUser;
    }

    public List<User> getUserFriends(Integer id) {
        List<User> friends = findMany(FIND_FRIENDS_BY_ID, id);
        friends.forEach(friend -> friend.setFriends(getFriendsById(friend.getId())));
        return friends;
    }

    public Set<Integer> getFriendsById(Integer id) {
        return new HashSet<>(findManyInstances(FIND_FRIENDS_ID, Integer.class, id));
    }

    @Override
    public void deleteUser(Integer userId) {
        delete(DELETE_USER_QUERY, userId);
    }

    @Override
    public User updateUser(User newUser) {
        update(
                UPDATE_QUERY,
                newUser.getName(),
                newUser.getEmail(),
                newUser.getLogin(),
                newUser.getBirthday(),
                newUser.getId()
        );
        newUser.setFriends(getFriendsById(newUser.getId()));
        return newUser;
    }

    @Override
    public void addFriend(User newUser, User friend) {
        insert(INSERT_FRIEND_QUERY, newUser.getId(), friend.getId());
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        delete(DELETE_FRIEND_QUERY, userId, friendId);
    }
}
