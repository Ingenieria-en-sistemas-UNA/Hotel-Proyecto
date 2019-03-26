package app.controller;

import app.dto.UserDataDTO;
import app.dto.UserResponseDTO;
import app.entity.User;
import app.service.UserService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<UserResponseDTO> login(//
                                                 @ApiParam("Username") @RequestParam String username,
                                                 @ApiParam("Password") @RequestParam String password) {
        UserResponseDTO responseDTO = userService.signin(username, password);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Algo malo ah sucedio"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "El usuario no existe"),
            @ApiResponse(code = 500, message = "JWT token expirado o invalido")})
    public ResponseEntity<UserResponseDTO> signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
        UserResponseDTO responseDTO = userService.signup(modelMapper.map(user, User.class));
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
    public ResponseEntity<UserResponseDTO> delete(@ApiParam("Username") @PathVariable String username) {
        UserResponseDTO responseDTO = userService.delete(username);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UserController.search}", response = UserResponseDTO.class)
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Algo malo ah sucedio"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "El usuario no existe"),
            @ApiResponse(code = 500, message = "JWT token expirado o invalido")})
    public UserResponseDTO search(@ApiParam("Username") @PathVariable String username) {
        return modelMapper.map(userService.search(username), UserResponseDTO.class);
    }

    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class)
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Algo malo ah sucedio"), //
            @ApiResponse(code = 403, message = "Acceso denegado"), //
            @ApiResponse(code = 500, message = "JWT token expirado o invalido")})
    public UserResponseDTO whoami(HttpServletRequest req) {
        return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<UserResponseDTO> refresh(HttpServletRequest req) {
        UserResponseDTO responseDTO = userService.refresh(req.getRemoteUser());
        return ResponseEntity.ok().body(responseDTO);
    }
}
