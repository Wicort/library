package ru.vovchinnikov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vovchinnikov.library.dao.BooksDAO;
import ru.vovchinnikov.library.dao.PersonDAO;
import ru.vovchinnikov.library.models.Book;
import ru.vovchinnikov.library.models.Person;
import ru.vovchinnikov.library.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    BooksDAO booksDAO;
    PersonDAO personDAO;
    BookValidator bookValidator;

    @Autowired
    public BooksController(BooksDAO booksDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.booksDAO = booksDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }


    @GetMapping()
    public String index(Model model) {
        System.out.println("get all books");
        model.addAttribute("books", booksDAO.getAll());

        return "books/index";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id,
                           Model model,
                           @ModelAttribute("person") Person person){
        System.out.println(String.format("get book info id=%d", id));
        Optional<Book> book = booksDAO.getBook(id);

        if (book.isPresent()){
            model.addAttribute("book", book.get());
            model.addAttribute("people", personDAO.getAll());

            Optional<Person> pers = personDAO.getPerson(book.get().getPersonId());
            if (pers.isPresent()) {
                System.out.println(String.format("book is attached to %s", pers.get().getFio()));
                model.addAttribute("personName", pers.get().getFio());
            }
        }

        return "books/show";
    }

    @PutMapping("/{id}/detach")
    public String detach(@PathVariable("id") int id){
        System.out.println(String.format("detach book %d", id));
        Optional<Book> book = booksDAO.getBook(id);
        if (book.isPresent()) {
            booksDAO.setReader(id, null);
            System.out.println("detached");
        }
        return String.format("redirect:/books/%d", id);
    }

    @PutMapping("/{id}/attach")
    public String attach(@PathVariable("id") int id,
                         @ModelAttribute("person") Person person){
        System.out.println(String.format("attach book %d to %d", id, person.getId()));
        Optional<Person> pers = personDAO.getPerson(person.getId());
        if (pers.isPresent()) {
            booksDAO.setReader(id, pers.get().getId());
            System.out.println("attached");
        }
        return String.format("redirect:/books/%d", id);
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        System.out.println("show new book form");

        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){
        System.out.println(String.format("creating new book: %s, %s, %d",
                book.getName(),
                book.getAuthor(),
                book.getYear()));

        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()){
            return "books/new";
        }
        booksDAO.createBook(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id,
                             Model model){
        Optional<Book> book = booksDAO.getBook(id);
        if (book.isPresent()) {
            booksDAO.deleteBook(id);
        }
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id,
                           Model model){
        Optional<Book> book = booksDAO.getBook(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "books/edit";
        } else {
            return String.format("redirect:/show/%d", id);
        }
    }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable("id") int id,
                             @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){
        System.out.println(String.format("updating book: %s, %s, %d",
                book.getName(),
                book.getAuthor(),
                book.getYear()));
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()){
            return "books/edit";
        }
        Optional<Book> findedBook = booksDAO.getBook(id);
        if (findedBook.isPresent()) {
            booksDAO.updateBook(id, book);
            return String.format("redirect:/books/%d", id);
        } else {
            return "books/edit";
        }
    }
}
