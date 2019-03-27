package app.service;

import app.dto.UserResponseDTO;
import app.entity.User;
import app.exeption.CustomException;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    UserResponseDTO signin(String username, String password) throws CustomException;
    UserResponseDTO signup(User user);
    UserResponseDTO delete(String username);
    User search(String username);
    User whoami(HttpServletRequest req);
    UserResponseDTO refresh(String username);

}
