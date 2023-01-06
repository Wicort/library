package ru.vovchinnikov.library.exceptions;

public class BookNotFoundException extends IllegalStateException {
    public BookNotFoundException(int id) {
        super(String.format("Книга с идентификатором %s не найдена", id));
    }
}
