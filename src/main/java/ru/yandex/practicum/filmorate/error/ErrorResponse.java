package ru.yandex.practicum.filmorate.error;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ErrorResponse {
    // название ошибки
    String error;
    // подробное описание
    String description;
}
