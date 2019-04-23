package app.controller;

import app.entity.Client;
import app.exeption.EntityNotFoundException;
import app.service.ClientService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/Client")
@Api(tags = "Client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Crea un Cliente", response = ResponseEntity.class, notes = "Retorna la persona añadida")
    public ResponseEntity<Client> save(
            @ApiParam(value = "Un obejto cliente tipo Json", required = true) @RequestBody Client client)
            throws DataIntegrityViolationException {
        Client clientResponse = clientService.save(client);
        return ResponseEntity.ok().body(clientResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Busca un cliente", response = Client.class, notes = "Retorna un cliente por ID")
    public  ResponseEntity<Client> get(@ApiParam(value = "El ID del cliente a buscar", required = true)@PathVariable("id")int id)
        throws EntityNotFoundException{

        Client client = clientService.get(id);
        return ResponseEntity.ok().body(client);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Actualiza un cliente", response = ResponseEntity.class, notes = "Retorna el cliente actualizado")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The client does not exist")})
    public ResponseEntity<Client> update ( @ApiParam(value = "El ID del cliente a actualizar", required = true) @PathVariable("id") int id,
                                           @ApiParam(value = "Un objeto tipo Json", required = true) @PathVariable("id") Client clientDTO)
        throws EntityNotFoundException{

        Client clientUpdate = clientService.update(id, clientDTO);
        return ResponseEntity.ok().body(clientUpdate);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Elimina un cliente", response = ResponseEntity.class, notes = "Retorna una respuesta OK")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The client does not exist")})
    public ResponseEntity<Client> delete(
            @ApiParam(value = "El ID del cliente a eliminar", required = true) @PathVariable("id") int id)
            throws EntityNotFoundException{

        Client client = clientService.delete(id);
        return ResponseEntity.ok().body(client);
    }



}
