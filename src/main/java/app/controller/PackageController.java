package app.controller;

import app.entity.Package;
import app.exeption.EntityNotFoundException;
import app.service.PackageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Package")
@Api(tags = "Package")
public class PackageController {

    @Autowired
    PackageService packageService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Crea un package", response = ResponseEntity.class, notes = "Retorna el package añadido")
    public ResponseEntity<Package> save(
            @ApiParam(value = "Un objeto package tipo Json", required = true) @RequestBody Package aPackage)
            throws DataIntegrityViolationException{

        Package packageResponse = packageService.save(aPackage);
        return ResponseEntity.ok().body(packageResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Busca un package", response = ResponseEntity.class, notes = "Retorna el package por el ID")
    public ResponseEntity<List<Package>> list(){
        List<Package> packages = packageService.list();
        return ResponseEntity.ok().body(packages);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Actualiza un package", response = ResponseEntity.class, notes = "Retorna el package actualizado")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The package does not exist")})
    public ResponseEntity<Package> update(@ApiParam(value = "El ID del package a actualizar", required = true)@PathVariable("id") int id,
                                          @ApiParam(value = "Un objeto tipo Json", required = true)@RequestBody Package packageDTO)
        throws EntityNotFoundException {

        Package aPackage = packageService.update(id, packageDTO);
        return ResponseEntity.ok().body(aPackage);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Elimina un package", response = ResponseEntity.class, notes = "Retorna una respuesta OK")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The package does not exist")})
    public ResponseEntity<Package> delete(
            @ApiParam(value = "El ID del package a eliminar", required = true) @PathVariable("id") int id)
            throws EntityNotFoundException {

        Package aPackage = packageService.delete(id);
        return ResponseEntity.ok().body(aPackage);
    }

}
