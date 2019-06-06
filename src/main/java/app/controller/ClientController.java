package app.controller;

import app.dto.FilterDate;
import app.entity.Client;
import app.entity.Room;
import app.exeption.EntityNotFoundException;
import app.service.ClientService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/client")
@Api(tags = "Client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Crea un Cliente", response = ResponseEntity.class, notes = "Retorna la persona añadida")
    public ResponseEntity<Client> save(
            @ApiParam(value = "Un obejto cliente tipo Json", required = true) @RequestBody Client client)
            throws DataIntegrityViolationException {

        Client clientResponse = clientService.save(client);
        return ResponseEntity.ok().body(clientResponse);
    }

    @PostMapping("/list")
    @CrossOrigin
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Busca todas personas", response = List.class, notes = "Retorna una lista de objetos Room")
    @ResponseBody
    public ResponseEntity<List<Client>> list(@RequestParam String filter,
                                           @RequestBody FilterDate filterDate) {

        List<Client> clients = clientService.list(filter, filterDate);
        return ResponseEntity.ok().body(clients);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Lista de clientes", response = Client.class, notes = "Retorna una lista clientes")
    public ResponseEntity<Client> get(@ApiParam(value = "El ID del cliente a buscar", required = true) @PathVariable("id") int id)
            throws EntityNotFoundException {

        Client clients = clientService.get(id);
        return ResponseEntity.ok().body(clients);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Actualiza un cliente", response = ResponseEntity.class, notes = "Retorna el cliente actualizado")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The client does not exist")})
    public ResponseEntity<Client> update(@ApiParam(value = "El ID del cliente a actualizar", required = true) @PathVariable("id") int id,
                                         @ApiParam(value = "Un objeto tipo Json", required = true) @RequestBody Client clientDTO)
            throws EntityNotFoundException {

        Client clientUpdate = clientService.update(id, clientDTO);
        return ResponseEntity.ok().body(clientUpdate);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Elimina un cliente", response = ResponseEntity.class, notes = "Retorna una respuesta OK")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The client does not exist")})
    public ResponseEntity<List<String>> delete(
            @ApiParam(value = "El ID del cliente a eliminar", required = true) @RequestBody List<String> idClients)
            throws EntityNotFoundException {

        clientService.delete(idClients);
        return ResponseEntity.ok().body(idClients);
    }

}
