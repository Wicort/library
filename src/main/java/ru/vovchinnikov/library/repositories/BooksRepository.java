package ru.vovchinnikov.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vovchinnikov.library.models.Book;
import ru.vovchinnikov.library.models.Person;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Person person);

    Book findBookByNameAndAuthor(String name, String author);

    List<Book> findByNameContainsIgnoreCase(String content);
}
