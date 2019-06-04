package app.service;

import app.dao.RoomDao;
import app.dto.FilterDate;
import app.entity.Room;
import app.exeption.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RoomServiceImp implements RoomService {

    @Autowired
    RoomDao roomDao;

    @Override
    public Room save(Room room) throws DataIntegrityViolationException {
        return roomDao.save(room);
    }

    @Override
    public Room get(int id) throws EntityNotFoundException {
        return roomDao.get(id);
    }

    @Override
    public Room update(int id, Room room) throws EntityNotFoundException {
        return roomDao.update(id,room);
    }

    @Override
    public void delete(List<Integer> idRooms) throws EntityNotFoundException {
        roomDao.delete(idRooms);
    }

    @Override
    public List<Room> list(String filter, FilterDate filterDate) {
        return roomDao.list(filter, filterDate);
    }
}
