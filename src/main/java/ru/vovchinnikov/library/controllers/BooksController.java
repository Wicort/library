package ru.vovchinnikov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vovchinnikov.library.exceptions.BookNotFoundException;
import ru.vovchinnikov.library.models.Book;
import ru.vovchinnikov.library.models.Person;
import ru.vovchinnikov.library.services.BooksService;
import ru.vovchinnikov.library.services.PeopleService;
import ru.vovchinnikov.library.util.BookValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService, BookValidator bookValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }


    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        if ((page == null) || (booksPerPage == null)) {
            System.out.println("get all books");
            if (sortByYear){
                model.addAttribute("books", booksService.findAllWithSortByYear());
            } else {
                model.addAttribute("books", booksService.findAll());
            }
        } else {
            if (sortByYear){
                model.addAttribute("books", booksService.findAllWithSortByYear(page, booksPerPage));
            } else {
                model.addAttribute("books", booksService.findAllWithPagination(page, booksPerPage));
            }
        }


        return "books/index";
    }

    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(value = "content", required = false) String content){
        System.out.println(content);
        if (content != null && !content.isEmpty()) {
            model.addAttribute("content", content);
            List<Book> books = booksService.findByContent(content);
            model.addAttribute("books", books);
        }
        return "books/search";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id,
                           Model model,
                           @ModelAttribute("person") Person person){
        System.out.printf("get book info id=%d%n", id);
        try {
            Book book = booksService.findOne(id);

            model.addAttribute("book", book);

            if (book.getOwner() != null) {
                Person pers = peopleService.findOne(book.getOwner().getId());

                System.out.printf("book is attached to %s%n", pers.getFio());
                model.addAttribute("personName", pers.getFio());
            } else {
                model.addAttribute("people", peopleService.findAll());
                model.addAttribute("personName", null);
            }
        } catch (BookNotFoundException e) {
            e.printStackTrace();
            return "books/404";
        }

        return "books/show";
    }

    @PutMapping("/{id}/detach")
    public String detach(@PathVariable("id") int id){
        System.out.printf("detach book %d%n", id);
        try {
            Book book = booksService.findOne(id);

            booksService.setReader(id, null);
            System.out.println("detached");
        } catch (BookNotFoundException e) {
            e.printStackTrace();
            return "books/404";
        }

        return String.format("redirect:/books/%d", id);
    }

    @PutMapping("/{id}/attach")
    public String attach(@PathVariable("id") int id,
                         @ModelAttribute("person") Person person){
        System.out.printf("attach book %d to %d%n", id, person.getId());
        Person pers = peopleService.findOne(person.getId());

        try {
            booksService.setReader(id, pers);
            System.out.println("attached");
        } catch (BookNotFoundException e) {
            e.printStackTrace();
            return "books/404";
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
        System.out.printf("creating new book: %s, %s, %d%n",
                book.getName(),
                book.getAuthor(),
                book.getYear());

        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()){
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id,
                             Model model){
        try {
            Book book = booksService.findOne(id);

            booksService.delete(id);
        } catch (BookNotFoundException e) {
            e.printStackTrace();
            return "books/404";
        }

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id,
                           Model model){
        try {
            Book book = booksService.findOne(id);
            if (book != null) {
                model.addAttribute("book", book);
            } else {
                return String.format("redirect:/show/%d", id);
            }
        } catch (BookNotFoundException e) {
            e.printStackTrace();
            return "books/404";
        }
        return "books/edit";
    }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable("id") int id,
                             @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){
        System.out.printf("updating book: %s, %s, %d%n",
                book.getName(),
                book.getAuthor(),
                book.getYear());
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()){
            return "books/edit";
        }
        try {
            Book findedBook = booksService.findOne(id);
            booksService.update(findedBook.getId(), book);
        } catch (BookNotFoundException e) {
            e.printStackTrace();
            return "books/404";
        }
        return String.format("redirect:/books/%d", id);
    }
}
