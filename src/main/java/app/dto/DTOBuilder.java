package app.dto;

import app.entity.User;

public class DTOBuilder {
    public static UserResponseDTO userToUserResponseDTO(User user){
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRoles());
    }
}
