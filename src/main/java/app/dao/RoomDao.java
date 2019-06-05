package app.dao;

import app.dto.FilterDate;
import app.entity.Room;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.List;

public interface RoomDao {

    Room save (Room room) throws DataIntegrityViolationException;

    Room get(int id) throws EntityNotFoundException;

    Room update(int id, Room room) throws EntityNotFoundException;

    void delete(List<Integer> idRooms) throws EntityNotFoundException;

    List<Room> list(String filter, FilterDate filterDate);
}
