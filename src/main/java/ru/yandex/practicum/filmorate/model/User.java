package ru.yandex.practicum.filmorate.model;


import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data

public class User {
    private Integer id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
}
