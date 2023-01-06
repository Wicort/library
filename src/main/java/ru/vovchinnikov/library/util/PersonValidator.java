package ru.vovchinnikov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vovchinnikov.library.models.Person;
import ru.vovchinnikov.library.services.PeopleService;

@Component
public class PersonValidator implements Validator {
    private PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        Person existedPerson = peopleService.findOne(person.getFio());

        if (existedPerson != null && person.getId() != existedPerson.getId()){
            errors.rejectValue("fio","","Читатель с таким именем уже заергистрирован");
        }
    }
}
