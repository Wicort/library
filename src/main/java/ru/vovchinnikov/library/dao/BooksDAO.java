package ru.vovchinnikov.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vovchinnikov.library.models.Book;

import java.util.List;
import java.util.Optional;

@Component
public class BooksDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT id, name, author, year, \"personId\" FROM book ORDER BY name",
                new BookMapper());
    }

    public Optional<Book> getBook(int id) {
        return jdbcTemplate.query("SELECT id, name, author, year, \"personId\" as personId FROM book WHERE id = ?",
                new Object[]{id},
                new BookMapper())
                .stream()
                .findAny();
    }

    public Optional<Book> getBook(String name, String author) {
        return jdbcTemplate.query("SELECT id, name, author, year, \"personId\" as personId " +
                        "FROM book WHERE name = ? and author = ?",
                new Object[]{name, author},
                new BookMapper())
                .stream()
                .findAny();
    }

    public void createBook (Book book){
        jdbcTemplate.update("INSERT INTO book (name, author, year) VALUES (?, ?, ?) ",
                book.getName(),
                book.getAuthor(),
                book.getYear());
    }

    public void updateBook (int id, Book book) {
        jdbcTemplate.update("UPDATE book set name = ?, author = ?, year = ? WHERE id = ?",
                book.getName(),
                book.getAuthor(),
                book.getYear(),
                id);
    }

    public void setReader(int bookId, Integer personId){
        jdbcTemplate.update("UPDATE book set \"personId\" = ? where id = ?", personId, bookId);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    public List<Book> getBooksByPerson(int personId) {
        return jdbcTemplate.query("SELECT id, name, author, year, \"personId\" FROM book WHERE \"personId\" = ?",
                new Object[]{personId},
                new BookMapper());
    }

}
