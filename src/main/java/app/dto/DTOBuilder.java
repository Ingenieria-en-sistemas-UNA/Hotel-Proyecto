package app.dto;

import app.entity.User;

public class DTOBuilder {
    public static UserDTO userToUserDTO(User user){
        return new UserDTO(user.getId(), user.getUsername(), user.getRoles(), user.getClient());
    }
}
