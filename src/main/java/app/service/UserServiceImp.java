package app.service;

import app.dao.UserRepository;
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
    private UserRepository userRepository;

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
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
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
        User user = userRepository.findByUsername(username);
        userRepository.deleteByUsername(username);
        UserResponseDTO responseDTO = DTOBuilder.userToUserResponseDTO(user);
        return responseDTO;
    }

    @Override
    @Transactional
    public User search(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("El usuario no fue encontrado", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    @Override
    @Transactional
    public User whoami(HttpServletRequest req) {
        return userRepository.findByUsername(this.tokenProvider.getUsername(this.tokenProvider.resolveToken(req)));
    }

    @Override
    @Transactional
    public UserResponseDTO refresh(String username) {
        UserResponseDTO responseDTO = getUserResponseDTO(username);
        return responseDTO;
    }

    private UserResponseDTO getUserResponseDTO(String username) {
        User user = userRepository.findByUsername(username);
        String token = this.tokenProvider.createToken(username, user.getRoles());
        UserResponseDTO responseDTO = DTOBuilder.userToUserResponseDTO(user);
        responseDTO.setToken(token);
        return responseDTO;
    }
}
