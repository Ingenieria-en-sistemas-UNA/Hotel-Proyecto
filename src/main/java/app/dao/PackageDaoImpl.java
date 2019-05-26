package app.dao;

import app.entity.Package;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PackageDaoImpl implements PackageDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Package save(Package aPackage) throws DataIntegrityViolationException {
        entityManager.persist(aPackage);
        entityManager.flush();
        return aPackage;
    }

    @Override
    public Package get(int id) throws EntityNotFoundException {
        Package aPackage = entityManager.find(Package.class, id);
        if (aPackage == null){
            throw new EntityNotFoundException(Package.class);
        }
        return aPackage;
    }

    @Override
    @Transactional
    public Package update(int id, Package requestPackage) throws EntityNotFoundException {
        Package aPackage = this.get(id);
        aPackage.setRoom(requestPackage.getRoom());
        aPackage.setClient(requestPackage.getClient());
        aPackage.setVoucher(requestPackage.getVoucher());
        aPackage.setNumberNight(requestPackage.getNumberNight());
        entityManager.merge(aPackage);
        return aPackage;
    }

    @Override
    @Transactional
    public Package delete(int id) throws EntityNotFoundException {
        Package aPackage = this.get(id);
        entityManager.remove(aPackage);
        return aPackage;
    }

    @Override
    public List<Package> list() {
        List<Package> aPackage = entityManager.createQuery("FROM Package").getResultList();
        return aPackage;
    }
}
