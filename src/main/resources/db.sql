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