package app.dao;

import app.dto.FilterDate;
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
        client.setMaxReserve(clientRequest.getMaxReserve());
        client.setLocalDate(clientRequest.getLocalDate());
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
    public List<Client> list(String filter, FilterDate filterDate) {
        if(filterDate.getInitialDate() != null && filterDate.getFinishDate() != null) {
            if (filter.equals("all")) {
                return entityManager.createQuery("SELECT r FROM Client r WHERE r.localDate >= :start AND r.localDate <= :end", Client.class)
                        .setParameter("start", filterDate.getInitialDate())
                        .setParameter("end", filterDate.getFinishDate())
                        .getResultList();//CHECK!!
            }
            return entityManager.createQuery("SELECT r FROM Client r WHERE r.email LIKE CONCAT('%',:searchKeyword, '%') AND r.localDate >= :start AND r.localDate <= :end", Client.class)
                    .setParameter("start", filterDate.getFinishDate())
                    .setParameter("end", filterDate.getFinishDate())
                    .getResultList();//CHECK!!

        }
        if (filter.equals("all")) {
            return entityManager.createQuery("FROM Client", Client.class).getResultList();//CHECK!!
        }
        return entityManager.createQuery("SELECT r FROM Client r WHERE r.email LIKE CONCAT('%',:searchKeyword, '%')", Client.class)
                .setParameter("searchKeyword", filter)
                .getResultList();
    }
}
