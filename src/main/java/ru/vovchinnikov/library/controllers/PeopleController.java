package ru.vovchinnikov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vovchinnikov.library.dao.BooksDAO;
import ru.vovchinnikov.library.dao.PersonDAO;
import ru.vovchinnikov.library.models.Book;
import ru.vovchinnikov.library.models.Person;
import ru.vovchinnikov.library.util.PersonValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final BooksDAO booksDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, BooksDAO booksDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.booksDAO = booksDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        System.out.println("get list of people");
        model.addAttribute("people", personDAO.getAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String showPerson(Model model,
            @PathVariable("id") int id) {
        System.out.println(String.format("get person id=%d", id));
        Optional<Person> person = personDAO.getPerson(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());

            List<Book> books = booksDAO.getBooksByPerson(person.get().getId());
            model.addAttribute("booksCount", books.size());
            model.addAttribute("books", books);
        }


        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        System.out.println("open new person form");

        return "people/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult){
        System.out.println(String.format("create new person fio=%s, bYear=%d",
                person.getFio(),
                person.getBirthYear()));

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        personDAO.addPerson(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        System.out.println(String.format("delete person id=%d", id));
        personDAO.deletePerson(id);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String openEditForm(Model model,
                               @PathVariable("id") int id) {
        System.out.println(String.format("open edit person form, id=%d", id));
        Optional<Person> person = personDAO.getPerson(id);
        if (person.isPresent()) {
            System.out.println("person is present");
            model.addAttribute("person", person.get());
        }

        return "people/edit";
    }

    @PutMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id,
                               @ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult){
        System.out.println(String.format("update person id=%d: %s, %d",
                person.getId(),
                person.getFio(),
                person.getBirthYear()));

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        personDAO.updatePerson(id, person);
        return String.format("redirect:/people/%d", id);
    }

}
