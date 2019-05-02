package app.dao;

import app.entity.Room;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomDaoImpl implements RoomDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Room save(Room room) throws DataIntegrityViolationException {
        entityManager.persist(room);
        return room;
    }

    @Override
    public Room get(int id) throws EntityNotFoundException {
        Room room = entityManager.find(Room.class, id);
        if(room == null){
            throw new EntityNotFoundException(Room.class); //Check!!
        }
        return room;
    }

    @Override
    @Transactional
    public Room update(int id, Room roomRequest) throws EntityNotFoundException {
        Room room = this.get(id);
        room.setType(roomRequest.getType());
        room.setPrice(roomRequest.getPrice());
        room.setGuests(roomRequest.getGuests());
        room.setState(roomRequest.getState());
        room.setDescription(roomRequest.getDescription());
        room.setImg(roomRequest.getImg());
        entityManager.merge(room);
        return room;
    }

    @Override
    @Transactional
    public Room delete(int id) throws EntityNotFoundException {
        Room room = this.get(id);
        entityManager.remove(room);
        return room;
    }

    @Override
    public List<Room> list(String filter) {
        List<Room> rooms =  new ArrayList<>();
        if(filter.equals("all")){
            rooms = entityManager.createQuery("FROM Room", Room.class).getResultList();//CHECK!!
        }else {
            rooms = entityManager.createQuery("SELECT r FROM Room r where r.type LIKE CONCAT('%',:searchKeyword, '%')", Room.class)
                                 .setParameter("searchKeyword", filter)
                                 .getResultList();
        }
            return rooms;
    }
}
