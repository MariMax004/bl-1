create sequence film_seq START 1;
create table film
(
    id           int primary key,
    data_release date         not null,
    name         varchar(128) not null
);

create sequence customer_seq start 1;
create table customer
(
    id         int primary key,
    email      varchar(128) not null,
    password   text not null,
    first_name varchar(128) not null,
    last_name  varchar(128) not null
);

create sequence comment_seq start 1;
create table comment
(
    id          int primary key,
    customer_id int references customer (id),
    film_id     int references film (id),
    rating      int  not null check (rating >= 0.5 and rating <= 5),
    comment     text not null
);

create sequence token_seq start 1;
create table token(
    id int primary key,
    customer_id int references customer(id),
    token text not null
)

