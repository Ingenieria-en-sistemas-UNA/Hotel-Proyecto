package app.controller;

import app.entity.Voucher;
import app.exeption.EntityNotFoundException;
import app.service.VoucherService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/voucher")
@Api(tags = "Voucher")
public class VoucherController {

    @Autowired
    VoucherService voucherService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Crea un voucher", response = ResponseEntity.class, notes = "Retorna el voucher añadido")
    public ResponseEntity<Voucher> save(
            @ApiParam(value = "Un objeto tipo Json", required = true) @RequestBody Voucher voucher)
        throws DataIntegrityViolationException{

        Voucher voucherResponse = voucherService.save(voucher);
        return ResponseEntity.ok().body(voucherResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Actualiza un voucher", response = ResponseEntity.class, notes = "Retorna voucher actualizado")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The voucher does not exist")})
    public ResponseEntity<Voucher> update(@ApiParam(value = "El ID del voucher a actualizar", required = true)@PathVariable("id") int id,
                                        @ApiParam(value = "Un objeto voucher tipo Json", required = true) @RequestBody Voucher voucherDTO)
        throws EntityNotFoundException{
        Voucher voucher = voucherService.update(id, voucherDTO);
        return ResponseEntity.ok().body(voucher);
    }

    @GetMapping
    @CrossOrigin
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "Busca todas facturas", response = List.class, notes = "Retorna una lista de objetos Voucher")
    public ResponseEntity<List<Voucher>> list(@RequestParam String filter) {
        List<Voucher> vouchers = voucherService.list(filter);
        return ResponseEntity.ok().body(vouchers);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Elimina un voucher", response = ResponseEntity.class, notes = "Retorna una respuesta OK")
    @ApiResponses({
            @ApiResponse(code = 500, message = "The voucher does not exist")})
    public ResponseEntity<Voucher> delete(
            @ApiParam(value = "El ID del voucher a eliminar", required = true) @PathVariable("id") int id)
        throws EntityNotFoundException{

        Voucher voucher = voucherService.delete(id);
        return ResponseEntity.ok().body(voucher);
    }
}
