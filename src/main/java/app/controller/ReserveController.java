package app.controller;

import app.entity.Reserve;
import app.exeption.EntityNotFoundException;
import app.service.ReserveService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/reserve")
@Api(tags = "Reserve")
public class ReserveController {

    @Autowired
    ReserveService reserveService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Crea una Reserva", response = ResponseEntity.class, notes = "Retorna la reserva añadida")
    public ResponseEntity<Reserve> save(
            @ApiParam(value = "Un objeto Reserve tipo Json", required = true) @RequestBody Reserve reserve)
            throws DataIntegrityViolationException, EntityNotFoundException {

        Reserve reserveResponse = reserveService.save(reserve);
        return ResponseEntity.ok().body(reserveResponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Lista de reservas", response = ResponseEntity.class, notes = "Retorna la lista de reservas")
    public ResponseEntity<List<Reserve>> list() {
        List<Reserve> reserve = reserveService.list();
        return ResponseEntity.ok().body(reserve);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Lista de reservas", response = ResponseEntity.class, notes = "Retorna la lista de reservas")
    public ResponseEntity<Reserve> get(@ApiParam(value = "El ID de la Reserva a buscar", required = true) @PathVariable("id") int id)
            throws EntityNotFoundException {
        Reserve reserve = reserveService.get(id);
        return ResponseEntity.ok().body(reserve);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Actualiza una Reserva", response = ResponseEntity.class, notes = "Retorna la reserva actualizada")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The Reserve does not exist")})
    public ResponseEntity<Reserve> update(@ApiParam(value = "El ID del Reserve a actualizar", required = true) @PathVariable("id") int id,
                                          @ApiParam(value = "Un objeto Reserve tipo Json", required = true) @RequestBody Reserve reserveRequest)
            throws EntityNotFoundException {

        Reserve reserve = reserveService.update(id, reserveRequest);
        return ResponseEntity.ok().body(reserve);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Elimina una reserva", response = ResponseEntity.class, notes = "Retorna la reserva eliminada")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The package does not exist")})
    public ResponseEntity<Reserve> delete(
            @ApiParam(value = "El ID del package a eliminar", required = true) @PathVariable("id") int id)
            throws EntityNotFoundException {

        Reserve reserve = reserveService.delete(id);
        return ResponseEntity.ok().body(reserve);
    }

}
