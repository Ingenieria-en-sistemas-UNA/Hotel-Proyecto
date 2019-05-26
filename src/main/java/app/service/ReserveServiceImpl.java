package app.service;

import app.dao.ReserveDao;
import app.entity.Reserve;
import app.exeption.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    ReserveDao reserveDao;

    @Override
    public Reserve save(Reserve reserve) throws DataIntegrityViolationException,EntityNotFoundException {
        return reserveDao.save(reserve);
    }

    @Override
    public Reserve get(int id) throws EntityNotFoundException {
        return reserveDao.get(id);
    }

    @Override
    public Reserve update(int id, Reserve reserve) throws EntityNotFoundException {
        return reserveDao.update(id, reserve);
    }

    @Override
    public Reserve delete(int id) throws EntityNotFoundException {
        return reserveDao.delete(id);
    }

    @Override
    public List<Reserve> list() {
        return reserveDao.list();
    }
}
