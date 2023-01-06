package ru.vovchinnikov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vovchinnikov.library.dao.BooksDAO;
import ru.vovchinnikov.library.models.Book;
import ru.vovchinnikov.library.services.BooksService;

@Component
public class BookValidator implements Validator {
    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        if (booksService.findOne(book.getName(), book.getAuthor()) != null) {
            errors.rejectValue("name", "", "Книга этого автора с таким названием уже зарегистрирована");
        }
    }
}
