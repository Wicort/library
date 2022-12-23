CREATE TABLE public.person (
    id int NOT NULL GENERATED ALWAYS AS IDENTITY,
    fio varchar UNIQUE,
    birthYear int not NULL
);

CREATE TABLE public.book (
    id int NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar not NULL,
    author varchar not NULL,
    year int not NULL
);

insert into person (fio, birthYear) values ('Овчинников Виктор Андреевич', 1985);
insert into person (fio, birthYear) values ('Иванов Иван Иванович', 1966);
insert into person (fio, birthYear) values ('Петров Константин Сергеевич', 2002);

select * from person