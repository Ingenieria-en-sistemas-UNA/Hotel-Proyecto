package app.service;

import app.entity.Reserve;
import app.entity.Room;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface ReserveService {

    Reserve save (Reserve reserve) throws DataIntegrityViolationException, EntityNotFoundException;

    Reserve get (int id) throws EntityNotFoundException;

    Reserve update (int id, Reserve reserve) throws EntityNotFoundException;

    Reserve unReserve(Reserve reserveRequest) throws EntityNotFoundException;

    List<Reserve> getClientReserves(int idClient) throws EntityNotFoundException;

    Reserve delete (int id) throws EntityNotFoundException;

    List<Reserve> list();
}
