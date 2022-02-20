-- auto-generated definition
-- create sequence rating_seq;
--
-- alter sequence comment_seq owner to s288870;

-- auto-generated definition
-- auto-generated definition
create table rating
(
    film_id     integer,
    customer_id integer,
    id          integer not null
        constraint rating_pk
            primary key,
    rating      integer not null,
    is_active boolean
);

alter table rating
    owner to s288870;



