package ru.vovchinnikov.library.models;

import org.springframework.stereotype.Component;

@Component
public class Person {

    private int id;

    private String FIO;

    private int birthYear;

    public Person() {
    }

    public Person(int id, String FIO, int birthYear) {
        this.id = id;
        this.FIO = FIO;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
