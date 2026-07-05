package dev.diyar68.tasktracker.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateTaskRequest {

    @NotBlank(message = "Description cannot be empty")
    private String description;

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }
}
