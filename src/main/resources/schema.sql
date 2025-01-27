drop table if exists users, films, likes, genres, mpa, user_friends, genre_films;

create table if not exists users (
  id int generated always as identity primary key,
  login varchar(100) not null unique,
  name varchar(100),
  email varchar(100) not null unique,
  birthday date
);

create table if not exists  mpa (
  id int generated always as identity primary key,
  name varchar(10)
);

create table if not exists  films (
  id int generated always as identity primary key,
  name varchar(100) not null,
  description varchar(200),
  release_date date,
  duration int not null,
  mpa_id int,
  foreign key (mpa_id) references mpa (id)
);

create table if not exists  likes (
  user_id int,
  film_id int,
  foreign key (film_id) references films (id),
  foreign key (user_id) references users (id),
  primary key (user_id, film_id)
);

create table if not exists  genres (
  id int generated always as identity primary key,
  name varchar(100) not null
);



create table if not exists  user_friends (
  user_id int,
  friend_id int,
  foreign key (user_id) references users (id),
  foreign key (friend_id) references users (id),
  primary key (user_id, friend_id),
  unique (user_id, friend_id)
);

create table if not exists  genre_films (
  genre_id int,
  film_id int,
  foreign key (genre_id) references genres (id),
  foreign key (film_id) references films (id),
  primary key (genre_id, film_id),
  unique (genre_id, film_id)
);