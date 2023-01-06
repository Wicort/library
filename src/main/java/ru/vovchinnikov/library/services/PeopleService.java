package ru.vovchinnikov.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vovchinnikov.library.models.Person;
import ru.vovchinnikov.library.repositories.PeopleRepository;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }
    public Person findOne(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    public Person findOne(String fio){
        return peopleRepository.findPersonByFio(fio);
    }

    @Transactional(readOnly = false)
    public void save(Person newPerson){
        peopleRepository.save(newPerson);
    }

    @Transactional(readOnly = false)
    public void update(int id, Person person){
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional(readOnly = false)
    public void delete(int id){
        peopleRepository.deleteById(id);
    }
}
