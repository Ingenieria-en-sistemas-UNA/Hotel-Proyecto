package app.service;

import app.entity.Person;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface PersonService {

    Person save (Person person) throws DataIntegrityViolationException;

    Person get (String id) throws EntityNotFoundException;

    Person update (String id, Person person) throws EntityNotFoundException;

    Person delete (String id) throws EntityNotFoundException;

    List<Person> list();
}
