package app.dao;

import app.entity.Reserve;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface ReserveDao {

    Reserve save (Reserve reserve) throws DataIntegrityViolationException, EntityNotFoundException;

    Reserve get (int id) throws EntityNotFoundException;

    Reserve update (int id, Reserve reserve) throws EntityNotFoundException;

    Reserve delete (int id) throws EntityNotFoundException;

    List<Reserve> list();
}
