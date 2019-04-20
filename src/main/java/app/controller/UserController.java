package app.controller;

import app.dto.DTOBuilder;
import app.dto.UserDTO;
import app.entity.User;
import app.exeption.CustomException;
import app.service.UserService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/user")
@Api(tags = "User")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/signin")
    @ApiOperation(value = "${UserController.signin}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Algo malo ah sucedio"),
            @ApiResponse(code = 422, message = "username/password invalido")})
    public ResponseEntity<UserDTO> login (//
                                          @ApiParam("Username") @RequestParam String username,
                                          @ApiParam("Password") @RequestParam String password) throws CustomException {
        UserDTO responseDTO = userService.signin(username, password);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Algo malo ah sucedio"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "El usuario no existe"),
            @ApiResponse(code = 500, message = "JWT token expirado o invalido")})
    public ResponseEntity<UserDTO> signup(@ApiParam("Signup User") @RequestBody User user) {
        UserDTO responseDTO = userService.signup(modelMapper.map(user, User.class));
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UserController.delete}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Algo malo ah sucedio"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "El usuario no existe"),
            @ApiResponse(code = 500, message = "JWT token expirado o invalido")})
    public ResponseEntity<UserDTO> delete(@ApiParam("Username") @PathVariable String username) {
        UserDTO responseDTO = userService.delete(username);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UserController.search}", response = UserDTO.class)
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Algo malo ah sucedio"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "El usuario no existe"),
            @ApiResponse(code = 500, message = "JWT token expirado o invalido")})
    public UserDTO search(@ApiParam("Username") @PathVariable String username) {
        return DTOBuilder.userToUserDTO(userService.search(username));
    }

    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${UserController.me}", response = UserDTO.class)
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Algo malo ah sucedio"), //
            @ApiResponse(code = 403, message = "Acceso denegado"), //
            @ApiResponse(code = 500, message = "JWT token expirado o invalido")})
    public UserDTO whoami(HttpServletRequest req) {
        return modelMapper.map(userService.whoami(req), UserDTO.class);
    }

    @GetMapping("/refresh/{userName}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<UserDTO> refresh(@PathVariable String userName) {
        UserDTO responseDTO = userService.refresh(userName);
        return ResponseEntity.ok().body(responseDTO);
    }
}
