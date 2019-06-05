package app.dao;

import app.entity.Client;
import app.entity.Reserve;
import app.entity.Room;
import app.exeption.CustomException;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReserveDaoImpl implements ReserveDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Reserve save(Reserve reserve) throws DataIntegrityViolationException, EntityNotFoundException {
        Room room = entityManager.find(Room.class, reserve.getRoom().getId());
        Client client = getClientOfReserve(reserve.getClient().getId());
        verifyNullEntities(room, client);
        if(room.getState()){
            throw new CustomException("La habitación esta ocupada", HttpStatus.CONFLICT);
        }
        if(client.getMaxReserve() == 0){
            throw new CustomException("EL cliente ya reservo las dos habitaciones por cuenta", HttpStatus.NOT_ACCEPTABLE);
        }
        reserve.setRoom(room);
        reserve.setClient(client);
        entityManager.persist(reserve);
        entityManager.flush();
        room.setState(true);
        updateClient(reserve, client);
        entityManager.merge(room);
        entityManager.merge(client);
        return reserve;
    }

    private void verifyNullEntities(Room room, Client client) throws EntityNotFoundException {
        if(room == null){
            throw new EntityNotFoundException(Room.class);
        }
        if(client == null){
            throw new EntityNotFoundException(Client.class);
        }
    }

    private List<Reserve> getReserves(Client client){
        List<Reserve> reserves = this.list();
        List<Reserve> returnReserves = new ArrayList<>();
        for (Reserve reserve: reserves ) {
            if (reserve.getClient().getId() == client.getId()) {
                returnReserves.add(reserve);
            }
        }
        return returnReserves;
    }

    private void updateClient(Reserve reserve, Client client) {
        client.setPerson(reserve.getClient().getPerson());
        client.setEmail(reserve.getClient().getEmail());
        client.setAddress(reserve.getClient().getAddress());
        client.setCellphone(reserve.getClient().getCellphone());
        client.setMaxReserve(client.getMaxReserve() - 1 );
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
    public Reserve unReserve(Reserve reserveRequest) throws EntityNotFoundException {
        Client client = getClientOfReserve(reserveRequest.getClient().getId());
        Reserve reserve = this.get(reserveRequest.getId());
        Room room = entityManager.find(Room.class, reserveRequest.getRoom().getId());
        verifyNullEntities(room, client);
        reserve.setAlive(false);
        client.setMaxReserve(client.getMaxReserve() + 1);
        room.setState(false);
        entityManager.merge(reserve);
        entityManager.merge(client);
        entityManager.merge(room);
        entityManager.flush();
        return reserve;
    }

    @Override
    public List<Reserve> getClientReserves(int idClient) throws EntityNotFoundException {
        Client client = getClientOfReserve(idClient);
        if(client == null){
            throw new EntityNotFoundException(Client.class);
        }
        return getReserves(client);
    }

    private Client getClientOfReserve(int idClient) {
        return entityManager.find(Client.class, idClient);
    }

    @Override
    @Transactional
    public Reserve update(int id, Reserve reserveRequest) throws EntityNotFoundException {
        Reserve reserve = this.get(id);
        reserve.setRoom(reserveRequest.getRoom());
        reserve.setClient(reserveRequest.getClient());
        reserve.setVoucher(reserveRequest.getVoucher());
        reserve.setAlive(reserveRequest.getAlive());
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
