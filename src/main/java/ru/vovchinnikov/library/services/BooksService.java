package ru.vovchinnikov.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vovchinnikov.library.exceptions.BookNotFoundException;
import ru.vovchinnikov.library.models.Book;
import ru.vovchinnikov.library.models.Person;
import ru.vovchinnikov.library.repositories.BooksRepository;
import ru.vovchinnikov.library.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public List<Book> findAllWithPagination(Integer page, Integer booksByPage){
        return booksRepository.findAll(PageRequest.of(page, booksByPage)).getContent();
    }

    public List<Book> findAllWithSortByYear(){
        return booksRepository.findAll(Sort.by("year"));
    }
    public List<Book> findAllWithSortByYear(Integer page, Integer booksByPage){
        return booksRepository.findAll(PageRequest.of(page, booksByPage, Sort.by("year"))).getContent();
    }

    public Book findOne(int id) throws BookNotFoundException {
        Optional<Book> book = booksRepository.findById(id);
        if (!book.isPresent()) {
            throw new BookNotFoundException(id);
        }
        return book.get();
    }

    public List<Book> findByPerson(Person person){
        return booksRepository.findByOwner(person);
    }

    public List<Book> findByContent(String content){
        return booksRepository.findByNameContainsIgnoreCase(content);
    }

    @Transactional(readOnly = false)
    public void save(Book newBook){
        booksRepository.save(newBook);
    }

    @Transactional(readOnly = false)
    public void update(int id, Book book){
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional(readOnly = false)
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void setReader(int id, Person person) throws BookNotFoundException {
        Book book = findOne(id);
        if (book != null) {
            book.setOwner(person);
            booksRepository.save(book);
        } else {
            throw new BookNotFoundException(id);
        }
    }

    public Book findOne(String name, String author){
        return booksRepository.findBookByNameAndAuthor(name, author);
    }

}
