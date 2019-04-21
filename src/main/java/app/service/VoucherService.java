package app.service;

import app.entity.Voucher;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface VoucherService {

    Voucher save (Voucher voucher) throws DataIntegrityViolationException;

    Voucher get (int id) throws EntityNotFoundException;

    Voucher update (int id, Voucher voucher) throws EntityNotFoundException;

    Voucher delete (int id) throws EntityNotFoundException;

    List<Voucher> list();
}
