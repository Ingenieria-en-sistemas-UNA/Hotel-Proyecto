package app.service;

import app.entity.Room;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface RoomService {

    Room save (Room room) throws DataIntegrityViolationException;

    Room get(int id) throws EntityNotFoundException;

    Room update(int id, Room room) throws EntityNotFoundException;
    
    void delete(List<Integer> idRooms) throws EntityNotFoundException;

    List<Room> list(String filter);
}
