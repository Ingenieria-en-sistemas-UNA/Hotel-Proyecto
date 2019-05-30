package app.service;

import app.dao.ClientDao;
import app.entity.Client;
import app.exeption.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientDao clientDao;

    @Override
    public Client save(Client client) throws DataIntegrityViolationException {
        return clientDao.save(client);
    }

    @Override
    public Client get(int id) throws EntityNotFoundException {
        return clientDao.get(id);
    }

    @Override
    public Client update(int id, Client client) throws EntityNotFoundException {
        return clientDao.update(id, client);
    }

    @Override
    public void delete(List<Integer> idClients) throws EntityNotFoundException {
        clientDao.delete(idClients);
    }

    @Override
    public List<Client> list() {
        return clientDao.list();
    }
}
