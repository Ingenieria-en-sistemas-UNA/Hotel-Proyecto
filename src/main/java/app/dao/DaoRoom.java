package app.dao;

import app.entity.Room;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface DaoRoom {

    Room save (Room room) throws DataIntegrityViolationException;

    Room get(int id) throws EntityNotFoundException;

    Room update(int id, Room room) throws EntityNotFoundException;

    Room delete(int id) throws EntityNotFoundException;

    List<Room> list();
}
