package app.dao;

import app.entity.Voucher;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VoucherDaoImpl implements VoucherDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Voucher save(Voucher voucher) throws DataIntegrityViolationException {
        entityManager.persist(voucher);
        return voucher;
    }

    @Override
    public Voucher get(int id) throws EntityNotFoundException {
        Voucher voucher = entityManager.find(Voucher.class, id);
        if(voucher == null){
            throw new EntityNotFoundException(Voucher.class);
        }
        return voucher;
    }

    @Override
    @Transactional
    public Voucher update(int id, Voucher voucherRequest) throws EntityNotFoundException {
        Voucher voucher = this.get(id);
        voucher.setEmitter(voucherRequest.getEmitter());
        voucher.setClient(voucherRequest.getClient());
        voucher.setLocalDate(voucherRequest.getLocalDate());
        voucher.setPrice(voucherRequest.getPrice());
        entityManager.merge(voucher);
        return voucher;
    }

    @Override
    @Transactional
    public Voucher delete(int id) throws EntityNotFoundException {
        Voucher voucher = this.get(id);
        entityManager.remove(voucher);
        return voucher;
    }

    @Override
    public List<Voucher> list() {
        List<Voucher> vouchers = entityManager.createQuery("FROM Voucher").getResultList();
        return vouchers;
    }
}
