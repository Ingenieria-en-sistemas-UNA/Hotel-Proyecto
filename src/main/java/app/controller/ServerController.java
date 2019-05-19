package app.controller;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/server")
@Api(tags = "Server")
public class ServerController {

    @GetMapping
    @ApiOperation(value = "${ServerController.getStatus}")
    public ResponseEntity<String> getStatus () {
        return ResponseEntity.ok().body("Encendido");
    }
}
