package app.dao;

import app.entity.Package;
import app.exeption.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface PackageDao {

    Package save (Package aPackage) throws DataIntegrityViolationException;

    Package get (int id) throws EntityNotFoundException;

    Package update (int id, Package aPackage) throws EntityNotFoundException;

    Package delete (int id) throws EntityNotFoundException;

    List<Package> list();
}
