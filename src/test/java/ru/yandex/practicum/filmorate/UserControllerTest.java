package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserControllerTest {
    private final UserController userController = new UserController();

    @Test
    public void validationCreateUserWithoutData() {
        User user = new User();
        assertThrows(RuntimeException.class, () -> userController.createUser(user));
    }

    @Test
    public void validationCreateUserWithNullName() {
        User user = new User();
        user.setLogin("dolore");
        user.setEmail("mail@mail.ru");
        user.setBirthday(LocalDate.of(1980, 2, 22));
        userController.createUser(user);
        assertEquals("dolore", user.getName(), "Имя и логин не совпадают");
    }

    @Test
    public void validationCreateUserWithBlankName() {
        User user = new User();
        user.setLogin("dolore");
        user.setName("   ");
        user.setEmail("mail@mail.ru");
        user.setBirthday(LocalDate.of(1980, 2, 22));
        userController.createUser(user);
        assertEquals("dolore", user.getName(), "Имя и логин не совпадают");
    }

    @Test
    public void validationCreateUserWithBlankEmail() {
        User user = new User();
        user.setLogin("dolore");
        user.setEmail(" ");
        user.setBirthday(LocalDate.of(1980, 2, 22));
        assertThrows(RuntimeException.class, () -> userController.createUser(user));
    }

    @Test
    public void validationCreateUserWithNullEmail() {
        User user = new User();
        user.setLogin("dolore");
        user.setBirthday(LocalDate.of(1980, 2, 22));
        assertThrows(RuntimeException.class, () -> userController.createUser(user));
    }

    @Test
    public void validationCreateUserWithoutSymbolOfDogInEmail() {
        User user = new User();
        user.setLogin("dolore");
        user.setEmail("mailmail.ru");
        user.setBirthday(LocalDate.of(1980, 2, 22));
        assertThrows(RuntimeException.class, () -> userController.createUser(user));
    }

    @Test
    public void validationCreateUserWithNullLogin() {
        User user = new User();
        user.setEmail("mail@mail.ru");
        user.setBirthday(LocalDate.of(1980, 2, 22));
        assertThrows(RuntimeException.class, () -> userController.createUser(user));
    }

    @Test
    public void validationCreateUserWithBlankLogin() {
        User user = new User();
        user.setLogin("  ");
        user.setEmail("mail@mail.ru");
        user.setBirthday(LocalDate.of(1980, 2, 22));
        assertThrows(RuntimeException.class, () -> userController.createUser(user));
    }

    @Test
    public void validationCreateUserWithWhitespaceInLogin() {
        User user = new User();
        user.setLogin("Dog mat");
        user.setEmail("mail@mail.ru");
        user.setBirthday(LocalDate.of(1980, 2, 22));
        assertThrows(RuntimeException.class, () -> userController.createUser(user));
    }
}
