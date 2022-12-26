--drop table public.person

CREATE TABLE public.person (
                               id int NOT NULL GENERATED ALWAYS AS identity primary key,
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

CREATE TABLE public.book (
                             id int NOT NULL GENERATED ALWAYS AS identity primary key,
                             name varchar not NULL,
                             author varchar not NULL,
                             year int not NULL,
                             person_id int references person(id) on delete SET NULL
);

insert into book (name, author, year, person_id) values ('Над пропастью во ржи', 'Джером Сэлинджер', 1951, null);
insert into book (name, author, year, person_id) values ('День опричника', 'Владимир Сорокин', 2006, null);
insert into book (name, author, year, person_id) values ('Тайные виды на гору Фудзи', 'Владимир Пелевин', 2018, null);
insert into book (name, author, year, person_id) values ('Психопатология обыденной жизни', 'Фрейд Зигмунд', 1904, null);
insert into book (name, author, year, person_id) values ('Игра в бисер', 'Герман Гессе', 1943, null);
insert into book (name, author, year, person_id) values ('Бытие и время', 'Мартин Хайдеггер', 1927, null);

select * from book b 