package ru.vovchinnikov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vovchinnikov.library.dao.PersonDAO;
import ru.vovchinnikov.library.models.Person;

@Component
public class PersonValidator implements Validator {
    private PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personDAO.getPerson(person.getFio()).isPresent()){
            errors.rejectValue("fio","","Читатель с таким именем уже заергистрирован");
        }
    }
}
