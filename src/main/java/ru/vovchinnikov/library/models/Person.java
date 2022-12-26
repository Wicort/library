package ru.vovchinnikov.library.models;

import javax.validation.constraints.*;

public class Person {

    private int id;

    @NotEmpty(message="Не заполнено ФИО читателя")
    @Size(min = 2, message = "Указано слишком короткое ФИО")
    private String fio;

    @Min(value = 1900, message = "Дата рождения не может быть меньше 1900 года")
    @Max(value = 2022, message = "Дата рождения не может быть больше 2022 года")
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
