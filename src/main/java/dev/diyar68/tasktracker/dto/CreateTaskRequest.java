package dev.diyar68.tasktracker.dto;

public class CreateTaskRequest {
    private String description;

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }
}
