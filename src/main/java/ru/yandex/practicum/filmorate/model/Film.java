package ru.yandex.practicum.filmorate.model;


import lombok.Data;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Film {

    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private Integer usersLikes;
    private Mpa mpa;
    private List<Genre> genres = new ArrayList<>();
}
