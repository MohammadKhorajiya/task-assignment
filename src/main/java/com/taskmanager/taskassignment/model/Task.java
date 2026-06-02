package com.taskmanager.taskassignment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection="tasks")
public class Task implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3,max = 100,message = "Title must be between 3 to 100 characters")
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime completedAt;
    @NotNull(message = "Priority is required")
    private Priority priority;
    private String username;

    public Task()
    {
    }

    public Task(String id,String title,String description,boolean completed)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatusMessage()
    {
        if(this.completed)
        {
            return "Completed";
        }
        else
        {
            return "Pending";
        }
    }
}
