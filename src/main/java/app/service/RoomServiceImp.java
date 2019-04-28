package app.service;

import app.dao.RoomDao;
import app.entity.Room;
import app.exeption.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Room delete(int id) throws EntityNotFoundException {
        return roomDao.delete(id);
    }

    @Override
    public List<Room> list(String filter) {
        return roomDao.list(filter);
    }
}
