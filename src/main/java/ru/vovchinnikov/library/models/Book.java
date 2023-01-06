package ru.vovchinnikov.library.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name="book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Необходимо заполнить название книги")
    private String name;

    @Column(name = "author")
    @NotEmpty(message = "Необходимо указать автора книги")
    private String author;

    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name="dateoftaken")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfTaken;

    public Book() {
    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person person) {
        this.owner = person;
    }

    public Date getDateOfTaken() {
        return dateOfTaken;
    }

    public void setDateOfTaken(Date dateOfTaken) {
        this.dateOfTaken = dateOfTaken;
    }

    public boolean isExpired() {
        Date sysdate = new Date();
        long delta = TimeUnit.DAYS.convert(Math.abs(sysdate.getTime() - this.dateOfTaken.getTime()), TimeUnit.MILLISECONDS);
        System.out.printf("delta is %d%n", delta);
        return delta >= 10;
    }

}
