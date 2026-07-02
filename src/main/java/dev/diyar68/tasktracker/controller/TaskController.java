package dev.diyar68.tasktracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.diyar68.tasktracker.entity.Task;
import dev.diyar68.tasktracker.service.TaskService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    private final TaskService taskService;
    
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public void createTask(@RequestBody String description) {
        taskService.createTask(description);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody String description) {
        taskService.updateTask(id, description);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PatchMapping("/{id}/done")
    public void markDone(@PathVariable Long id) {
        taskService.markDone(id);
    }

    @PatchMapping("/{id}/in-progress")
    public void markInProgress(@PathVariable Long id) {
        taskService.markInProgress(id);
    }
}
