package app.dto;

import app.entity.Client;
import app.entity.Role;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UserDTO {
    @ApiModelProperty(position = 0)
    private Integer id;
    @ApiModelProperty(position = 1)
    private String username;
    @ApiModelProperty(position = 2)
    private List<Role> roles;
    @ApiModelProperty(position = 3)
    private String token;
    @ApiModelProperty(position = 4)
    private Client client;

    public UserDTO(Integer id, String username, List<Role> roles, Client client) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
