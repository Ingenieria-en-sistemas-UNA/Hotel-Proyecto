package app.dao;

import app.dto.FilterDate;
import app.entity.Room;
import app.exeption.EntityNotFoundException;
import app.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomDaoImpl implements RoomDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    @Transactional
    public Room save(Room room) throws DataIntegrityViolationException {
        entityManager.persist(room);
        entityManager.flush();
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
        room.setLocalDate(roomRequest.getLocalDate());
        entityManager.merge(room);
        return room;
    }

    @Override
    public List<Room> list(String filter, FilterDate filterDate) {
        if(filterDate.getInitialDate() != null && filterDate.getFinishDate() != null) {
            if (filter.equals("all")) {
                return entityManager.createQuery("SELECT r FROM Room r WHERE r.localDate >= :start AND r.localDate <= :end", Room.class)
                        .setParameter("start", filterDate.getInitialDate())
                        .setParameter("end", filterDate.getFinishDate())
                        .getResultList();//CHECK!!
            }
            return entityManager.createQuery("SELECT r FROM Room r WHERE r.type LIKE CONCAT('%',:searchKeyword, '%') AND r.localDate BETWEEN :start AND :end", Room.class)
                    .setParameter("start", filterDate.getFinishDate())
                    .setParameter("end", filterDate.getFinishDate())
                    .getResultList();//CHECK!!

        }
        if (filter.equals("all")) {
            return entityManager.createQuery("FROM Room", Room.class).getResultList();//CHECK!!
        }
        return entityManager.createQuery("SELECT r FROM Room r where r.type LIKE CONCAT('%',:searchKeyword, '%')", Room.class)
                .setParameter("searchKeyword", filter)
                .getResultList();
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void delete(List<Integer> idRooms) throws EntityNotFoundException {
        int i=0;
        for(Integer id : idRooms) {
            if(++i%49==0) {
                entityManager.flush();
            }
            Room room = this.get(id);
            fileStorageService.deleteFile(room.getImg());
            entityManager.remove(room);
        }
    }
}
