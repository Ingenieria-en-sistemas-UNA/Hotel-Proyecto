package app.dao;

import app.entity.Client;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface ClientDao {

    Client save (Client client) throws DataIntegrityViolationException;

    Client get (int id) throws EntityNotFoundException;

    Client update (int id, Client client) throws EntityNotFoundException;

    Client delete (int id) throws EntityNotFoundException;

    List<Client> list();
}
