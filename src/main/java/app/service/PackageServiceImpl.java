package app.service;

import app.dao.PackageDao;
import app.entity.Package;
import app.exeption.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PackageServiceImpl implements PackageService{

    @Autowired
    PackageDao packageDao;

    @Override
    public Package save(Package aPackage) throws DataIntegrityViolationException {
        return packageDao.save(aPackage);
    }

    @Override
    public Package get(int id) throws EntityNotFoundException {
        return packageDao.get(id);
    }

    @Override
    public Package update(int id, Package aPackage) throws EntityNotFoundException {
        return packageDao.update(id, aPackage);
    }

    @Override
    public Package delete(int id) throws EntityNotFoundException {
        return packageDao.delete(id);
    }

    @Override
    public List<Package> list() {
        return packageDao.list();
    }
}
