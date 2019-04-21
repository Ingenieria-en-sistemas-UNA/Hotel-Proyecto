package app.service;

import app.dao.PersonDao;
import app.entity.Person;
import app.exeption.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService{

    @Autowired
    PersonDao personDao;

    @Override
    public Person save(Person person) throws DataIntegrityViolationException {
        return personDao.save(person);
    }

    @Override
    public Person get(String id) throws EntityNotFoundException {
        return personDao.get(id);
    }

    @Override
    public Person update(String id, Person person) throws EntityNotFoundException {
        return personDao.update(id, person);
    }

    @Override
    public Person delete(String id) throws EntityNotFoundException {
        return personDao.delete(id);
    }

    @Override
    public List<Person> list() {
        return personDao.list();
    }
}
