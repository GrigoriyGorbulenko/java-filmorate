package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.mappers.UserRowMapper;


import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase
@Import({UserDbStorage.class, UserRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTest {
    private final UserStorage userStorage;

    @Test
    void testFindAllUser() {
        assertEquals(5,  userStorage.getAllUsers().size(), "Неверное  пользователей");
    }

    @Test
     void testFindUserById() {

        Optional<User> userOptional = userStorage.getUserById(1);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    void testCreateUser() {

       User user = userStorage.createUser(getTestUser2());
        assertThat(user).
                hasFieldOrPropertyWithValue("id", 6);
    }

    @Test
    void testUpdateUser() {
        assertThat(userStorage.updateUser(getTestUser()))
                .hasFieldOrPropertyWithValue("name", "Griiiiiiiiiii")
                .hasFieldOrPropertyWithValue("login", "Grin")
                .hasFieldOrPropertyWithValue("email", "grin@yandex.ru");
    }

    @Test
    void testCreateFriend() {
        userStorage.createFriend(1, 2);

        assertNotNull(userStorage.getFriendsById(1));
        assertEquals(userStorage.getFriendsById(2).size(),0);

    }

    @Test
    void testDeleteFriend() {
        userStorage.createFriend(1, 2);
        assertNotNull(userStorage.getFriendsById(1));

        userStorage.deleteFriend(1, 2);
        assertEquals(userStorage.getFriendsById(1).size(),0);
    }

    @Test
    void testFindCommonFriend() {
        userStorage.createFriend(1, 2);
        userStorage.createFriend(1, 3);
        userStorage.createFriend(2, 3);
        assertEquals(userStorage.getCommonFriends(1, 2).size(),1);
    }



    private static User getTestUser() {
        User user = new User();
        user.setId(1);
        user.setLogin("Grin");
        user.setName("Griiiiiiiiiii");
        user.setEmail("grin@yandex.ru");
        user.setBirthday(LocalDate.of(2000, 3, 2));

        return user;
    }

    private static User getTestUser2() {
        User user = new User();
        user.setEmail("bro2@yandex.ru");
        user.setLogin("bro2");
        user.setName("broooo2");
        user.setBirthday(LocalDate.of(2022, 11, 3));
        return user;
    }
}
