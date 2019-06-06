package app.service;

import app.dao.VoucherDao;
import app.dto.FilterDate;
import app.entity.Voucher;
import app.exeption.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    VoucherDao voucherDao;

    @Override
    public Voucher save(Voucher voucher) throws DataIntegrityViolationException {
        return voucherDao.save(voucher);
    }

    @Override
    public Voucher get(int id) throws EntityNotFoundException {
        return voucherDao.get(id);
    }

    @Override
    public Voucher update(int id, Voucher voucher) throws EntityNotFoundException {
        return voucherDao.update(id, voucher);
    }

    @Override
    public Voucher delete(int id) throws EntityNotFoundException {
        return voucherDao.delete(id);
    }

    @Override
    public List<Voucher> list(String filter, FilterDate filterDate) {
        return voucherDao.list(filter, filterDate);
    }
}
