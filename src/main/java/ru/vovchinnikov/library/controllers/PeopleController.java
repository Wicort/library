package ru.vovchinnikov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vovchinnikov.library.dao.PersonDAO;
import ru.vovchinnikov.library.models.Person;

import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personDAO.getAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String showPerson(Model model,
            @PathVariable("id") int id) {
        Optional<Person> person = personDAO.getPerson(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
        }

        return "people/show";
    }
}
