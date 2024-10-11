package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Repository
public class UserDbStorage extends BaseDbStorage<User> implements UserStorage {

    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String INSERT_QUERY = """
            INSERT INTO users (email, name, login, birthday)
            VALUES (?,?,?,?) returning id
            """;
    private static final String UPDATE_QUERY = """
            UPDATE users SET login = ?, name = ?, email = ?, birthday = ?
            WHERE id = ?
            """;

    private static final String INSERT_FRIEND_QUERY = """
            INSERT INTO user_friends (user_id, friend_id)
            VALUES (?,?) returning friend_id
            """;
    private static final String DELETE_FRIEND_QUERY = """
            DELETE FROM user_friends
            WHERE user_id = ? AND friend_id = ?
            """;

    private static final String FIND_FRIENDS_BY_ID = """
            SELECT u.id, u.email, u.name, u.login, u.birthday
            FROM user_friends  AS uf
            LEFT JOIN users AS u ON uf.friend_id = u.id
            WHERE uf.user_id = ?
            ORDER BY u.id
            """;

    private static final String FIND_COMMON_FRIENDS_BY_ID = """
            SELECT u.id, u.email, u.name, u.login, u.birthday
            FROM user_friends as uf
            LEFT JOIN users AS u ON uf.friend_id = u.id
            WHERE uf.user_id = ?
            AND uf.friend_id IN (SELECT friend_id FROM user_friends WHERE user_id = ?);
            """;



    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper, User.class);
    }

    @Override
    public Collection<User> getAllUsers() {
        return findMany(FIND_ALL_QUERY);
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
        return newUser;
    }
    @Override
    public void createFriend(Integer userId, Integer friendId) {
        insert(
                INSERT_FRIEND_QUERY,
                userId,
                friendId
                );
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        delete(DELETE_FRIEND_QUERY, userId, friendId);
    }

    @Override
    public List<User> getFriendsById(Integer userId) {
        return findMany(FIND_FRIENDS_BY_ID, userId);

    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        return findMany(FIND_COMMON_FRIENDS_BY_ID, userId, friendId);
    }

}
