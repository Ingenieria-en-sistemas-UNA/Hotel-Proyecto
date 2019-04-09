package app.service;

import app.dao.UserDao;
import app.dto.DTOBuilder;
import app.dto.UserResponseDTO;
import app.entity.User;
import app.security.TokenProvider;
import app.exeption.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImp implements UserService {
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    @Transactional
    public UserResponseDTO signin(String username, String password) throws CustomException{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserResponseDTO responseDTO = getUserResponseDTO(username);
            return responseDTO;
        } catch (AuthenticationException e) {
            throw new CustomException("Usuario o contraseña incorrectos", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    @Transactional
    public UserResponseDTO signup(User user) {
        if (!userDao.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.save(user);
            String token = this.tokenProvider.createToken(user.getUsername(), user.getRoles());
            UserResponseDTO responseDTO = DTOBuilder.userToUserResponseDTO(user);
            responseDTO.setToken(token);
            return responseDTO;
        } else {
            throw new CustomException("El usuario ya esta en uso", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    @Transactional
    public UserResponseDTO delete(String username) {
        User user = userDao.findByUsername(username);
        userDao.deleteByUsername(username);
        UserResponseDTO responseDTO = DTOBuilder.userToUserResponseDTO(user);
        return responseDTO;
    }

    @Override
    @Transactional
    public User search(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new CustomException("El usuario no fue encontrado", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    @Override
    @Transactional
    public User whoami(HttpServletRequest req) {
        return userDao.findByUsername(this.tokenProvider.getUsername(this.tokenProvider.resolveToken(req)));
    }

    @Override
    @Transactional
    public UserResponseDTO refresh(String username) {
        UserResponseDTO responseDTO = getUserResponseDTO(username);
        return responseDTO;
    }

    private UserResponseDTO getUserResponseDTO(String username) {
        User user = userDao.findByUsername(username);
        String token = this.tokenProvider.createToken(username, user.getRoles());
        UserResponseDTO responseDTO = DTOBuilder.userToUserResponseDTO(user);
        responseDTO.setToken(token);
        return responseDTO;
    }
}
