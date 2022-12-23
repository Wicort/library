package ru.vovchinnikov.library.models;

import org.springframework.stereotype.Component;

@Component
public class Person {

    private int id;

    private String fio;

    private int birthYear;

    public Person() {
    }

    public Person(int id, String fio, int birthYear) {
        this.id = id;
        this.fio = fio;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
