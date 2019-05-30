package app.dao;

import app.entity.Client;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ClientDaoImpl implements ClientDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Client save(Client client) throws DataIntegrityViolationException {
        entityManager.persist(client);
        return client;
    }

    @Override
    public Client get(int id) throws EntityNotFoundException {
        Client client = entityManager.find(Client.class, id);
        if(client == null){
            throw new EntityNotFoundException(Client.class);
        }
        return client;
    }

    @Override
    @Transactional
    public Client update(int id, Client clientRequest) throws EntityNotFoundException {
        Client client = this.get(id);
        client.setEmail(clientRequest.getEmail());
        client.setAddress(clientRequest.getAddress());
        client.setPerson(clientRequest.getPerson());
        client.setCellphone(clientRequest.getCellphone());
        entityManager.merge(client);
        return client;
    }

    @Override
    @Transactional
    public void delete(List<Integer> idClients) throws EntityNotFoundException {
        int i=0;
        for(Integer id : idClients) {
            if(++i%49==0) {
                entityManager.flush();
            }
            Client client = entityManager.getReference(Client.class, id);
            entityManager.remove(client);
        }
    }

    @Override
    public List<Client> list() {
        List<Client> clients = entityManager.createQuery("FROM Client", Client.class).getResultList();
        return clients;
    }
}
