package com.taskmanager.taskassignment.model;

import java.util.List;

public class UserResponseDTO
{
    private String id;
    public String username;
    public String email;
    public List<String> roles;

    public UserResponseDTO(User user)
    {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
