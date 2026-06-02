package com.taskmanager.taskassignment.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "task_users")
public class User
{
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private String email;
    private List<String> roles;

    @DBRef
    private List<Task> taskEntries=new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<Task> getTaskEntries()
    {
        return taskEntries;
    }
    public void setTaskEntries(List<Task> taskEntries)
    {
        this.taskEntries = taskEntries;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
}
