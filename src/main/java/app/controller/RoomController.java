package app.controller;

import app.entity.Room;
import app.exeption.EntityNotFoundException;
import app.service.FileStorageService;
import app.service.RoomService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/room")
@Api(tags = "Room")
public class RoomController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private RoomService roomService;

    @PostMapping(consumes = { "multipart/form-data"})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Crea una habitación", response = ResponseEntity.class, notes = "Retorna la habitación añadida")
    @ResponseBody
    public ResponseEntity<Room> save(
            @ApiParam(value = "Un objeto Habitacion tipo Json", required = true) @Valid @RequestPart("file") MultipartFile file,
            @RequestPart Room room)
            throws DataIntegrityViolationException, IOException {

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        room.setImg(fileName);
        Room roomResponse = roomService.save(room);
        return ResponseEntity.ok().body(roomResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Busca una Habitación", response = Room.class, notes = "Retorna una habitación por ID")
    public ResponseEntity<Room> get(@ApiParam(value = "El ID de la habitación a buscar", required = true) @PathVariable("id") int id)
            throws EntityNotFoundException {

        Room room = roomService.get(id);
        return ResponseEntity.ok().body(room);

    }

    @GetMapping
    @CrossOrigin
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Busca todas personas", response = List.class, notes = "Retorna una lista de objetos Room")
    public ResponseEntity<List<Room>> list(@RequestParam String filter) {
        List<Room> rooms = roomService.list(filter);
        return ResponseEntity.ok().body(rooms);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Actualiza una habitación", response = ResponseEntity.class, notes = "Restorna la habitación actualizada")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The room does not exist")})
    public ResponseEntity<Room> update(@ApiParam(value = "El ID de la habitación a actualizar", required = true) @PathVariable("id") int id,
                                       @ApiParam(value = "Un objeto Room tipo Json", required = true) @RequestBody Room personDTO)
            throws EntityNotFoundException {

        Room personDTOUpdatedResponse = roomService.update(id, personDTO);
        return ResponseEntity.ok().body(personDTOUpdatedResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Elimina una Habitación", response = ResponseEntity.class, notes = "Retorna una respuesta OK")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The Room does not exist")})
    public ResponseEntity<Room> delete(
            @ApiParam(value = "El ID de la persona a eliminar", required = true) @PathVariable("id") int id)
            throws EntityNotFoundException {

        Room room = roomService.delete(id);
        return ResponseEntity.ok().body(room);
    }
}
