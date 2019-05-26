package app.dao;

import app.entity.Client;
import app.entity.Reserve;
import app.entity.Room;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ReserveDaoImpl implements ReserveDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Reserve save(Reserve reserve) throws DataIntegrityViolationException, EntityNotFoundException {
        Room room = entityManager.find(Room.class, reserve.getRoom().getId());
        Client client = entityManager.find(Client.class, reserve.getClient().getId());
        if(room == null){
            throw new EntityNotFoundException(Room.class);
        }
        if(client == null){
            throw new EntityNotFoundException(Client.class);
        }
        reserve.setRoom(room);
        reserve.setClient(client);
        entityManager.persist(reserve);
        entityManager.flush();
        client.setId_package(reserve.getId());
        entityManager.merge(client);

        return reserve;
    }

    @Override
    public Reserve get(int id) throws EntityNotFoundException {
        Reserve reserve = entityManager.find(Reserve.class, id);
        if (reserve == null){
            throw new EntityNotFoundException(Reserve.class);
        }
        return reserve;
    }

    @Override
    @Transactional
    public Reserve update(int id, Reserve reserveRequest) throws EntityNotFoundException {
        Reserve reserve = this.get(id);
        reserve.setRoom(reserveRequest.getRoom());
        reserve.setClient(reserveRequest.getClient());
        reserve.setVoucher(reserveRequest.getVoucher());
        entityManager.merge(reserve);
        return reserve;
    }

    @Override
    @Transactional
    public Reserve delete(int id) throws EntityNotFoundException {
        Reserve reserve = this.get(id);
        entityManager.remove(reserve);
        return reserve;
    }

    @Override
    public List<Reserve> list() {
        List<Reserve> reserve = entityManager.createQuery("FROM Reserve").getResultList();
        return reserve;
    }
}
