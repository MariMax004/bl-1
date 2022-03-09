-- auto-generated definition
-- create sequence rating_seq;
--
-- alter sequence comment_seq owner to s288870;

-- auto-generated definition
-- auto-generated definition
create sequence comment_seq;

create sequence rating_seq;

alter table rating
    owner to s288870;


create table comment
(
    is_active   boolean default false,
    customer_id integer
        constraint comment_customer_id_fkey
            references customer,
    comment     text    not null,
    id          integer not null
        constraint comment_pkey
            primary key,
    film_id     integer
        constraint comment_film_id_fkey
            references film,

    is_moderated  boolean default false

);

alter table comment
    owner to s288870;



