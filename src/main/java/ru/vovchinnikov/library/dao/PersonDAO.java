package ru.vovchinnikov.library.dao;

import org.springframework.stereotype.Component;

@Component
public class PersonDAO {
/*
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT id, fio, birthYear FROM person ORDER BY fio",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> getPerson(int id) {
        return jdbcTemplate.query("SELECT id, fio, birthyear FROM person WHERE id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny();
    }

    public Optional<Person> getPerson(String fio) {
        return jdbcTemplate.query("SELECT id, fio, birthyear FROM person WHERE fio = ?",
                new Object[]{fio},
                new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny();
    }

    public boolean isExists(String fio){
        Optional<Person> person = getPerson(fio);

        return person.isPresent();
    }

    public void addPerson(Person person){
        jdbcTemplate.update("INSERT INTO person (fio, birthyear) VALUES (?, ?)",
                person.getFio(),
                person.getBirthYear());
    }

    public void updatePerson(int id, Person person) {
        jdbcTemplate.update("UPDATE person set fio = ?, birthyear = ? WHERE id = ?",
                person.getFio(),
                person.getBirthYear(),
                id);
    }

    public void deletePerson(int id){
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }
*/
}
