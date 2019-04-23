package app.controller;

import app.entity.Person;
import app.exeption.EntityNotFoundException;
import app.service.PersonService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Person")
@Api(tags = "Person")
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Crea una Persona", response = ResponseEntity.class, notes = "Retorna la persona añadida")
    public ResponseEntity<Person> save(
        @ApiParam(value = "Un objeto persona tipo Json", required = true) @RequestBody Person person)
        throws DataIntegrityViolationException {

        Person personResponse = personService.save(person);
        return ResponseEntity.ok().body(personResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Busca una persona", response = Person.class, notes = "Retorna una persona por el ID")
    public ResponseEntity<List<Person>> list(){
        List<Person> persons = personService.list();
        return ResponseEntity.ok().body(persons);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Actualiza una persona", response = ResponseEntity.class, notes = "Retorna la persona actualizada")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The person does not exist")})
    public ResponseEntity<Person> update (@ApiParam(value = "El ID de la persona a actualizar", required = true) @PathVariable("id")String id,
                                          @ApiParam(value = "Un objeto tipo Json", required = true) @RequestBody Person personDTO)
        throws EntityNotFoundException{

        Person personUpdate = personService.update(id, personDTO);
        return ResponseEntity.ok().body(personUpdate);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Elimina una persona", response = ResponseEntity.class, notes = "Retorna una respuesta OK")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The person does not exist")})
    public ResponseEntity<Person> delete(
            @ApiParam(value = "El ID de la persona a eliminar", required = true) @PathVariable("id")String id)
            throws EntityNotFoundException{
        Person person = personService.delete(id);
        return ResponseEntity.ok().body(person);
    }

}
