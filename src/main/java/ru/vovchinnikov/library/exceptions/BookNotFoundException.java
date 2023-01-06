package ru.vovchinnikov.library.exceptions;

import javassist.NotFoundException;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(int id) {
        super(String.format("Книга с идентификатором %s не найдена", id));
    }
}
