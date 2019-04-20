package app.service;

import app.dto.UserDTO;
import app.entity.User;
import app.exeption.CustomException;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    UserDTO signin(String username, String password) throws CustomException;
    UserDTO signup(User user);
    UserDTO delete(String username);
    User search(String username);
    User whoami(HttpServletRequest req);
    UserDTO refresh(String username);

}
