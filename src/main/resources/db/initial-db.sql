create table api_user
(
    id serial not null
        constraint api_user_id_pk
            primary key,
    name varchar(200) not null,
    phone varchar(50),
    website varchar(200),
    ext_id integer
);

alter table api_user owner to postgres;

create unique index api_user_id_uindex
    on api_user (id);

create table address
(
    id serial not null
        constraint address_id_pk
            primary key,
    street varchar(200) not null,
    city varchar(200) not null,
    suite varchar(150),
    zipcode varchar(10),
    api_user_id integer
);

alter table address owner to postgres;

create unique index address_id_uindex
    on address (id);