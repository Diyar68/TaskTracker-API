package dev.diyar68.tasktracker.exception;

public class TaskNotFoundException extends RuntimeException {
    
    public TaskNotFoundException(Long id) {
        super("Task with id " + id + " does not exist");
    }
}
