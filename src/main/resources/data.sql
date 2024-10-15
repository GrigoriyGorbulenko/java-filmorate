insert into mpa (name) values('G'), ('PG'), ('PG-13'), ('R'), ('NC-17');
insert into genres (name) values('Комедия'), ('Драма'), ('Мультфильм'), ('Триллер'), ('Документальный'), ('Боевик');

insert into users (login, name, email, birthday) values ('Grin', 'Gri', 'grin@yandex.ru', '1955-08-20');
insert into users (login, name, email, birthday) values ('Grib', 'Grib', 'grib@yandex.ru', '1933-08-20');
insert into users (login, name, email, birthday) values ('Grib1', 'Grib1', 'grib1@yandex.ru', '1934-08-20');
insert into users (login, name, email, birthday) values ('Grib2', 'Grib2', 'grib2@yandex.ru', '1954-08-20');
insert into users (login, name, email, birthday) values ('Grib3', 'Grib3', 'grib3@yandex.ru', '1944-08-20');

insert into films (name, description, release_date, duration, mpa_id) values ('Гладиатор', 'Исторический', '1922-08-20',
 180, 3);
insert into films (name, description, release_date, duration, mpa_id) values ('Гладиатор1', 'Исторический1', '1922-08-20',
 170, 2);
insert into films (name, description, release_date, duration, mpa_id) values ('Гладиатор2', 'Исторический2', '1922-08-20',
 160, 4);
insert into films (name, description, release_date, duration, mpa_id) values ('Гладиатор3', 'Исторический3', '1922-08-20',
  150, 5);
insert into films (name, description, release_date, duration, mpa_id) values ('Гладиатор4', 'Исторический5', '1922-08-20',
   140, 1);


 insert into genre_films(genre_id, film_id) values (3, 1);
 insert into genre_films(genre_id, film_id) values (4, 1);

insert into likes(user_id, film_id) values (3, 4);
insert into likes(user_id, film_id) values (3, 1);
insert into likes(user_id, film_id) values (3, 3);
insert into likes(user_id, film_id) values (2, 1);
insert into likes(user_id, film_id) values (2, 4);
insert into likes(user_id, film_id) values (1, 4);
