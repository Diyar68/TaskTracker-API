package dev.diyar68.tasktracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.diyar68.tasktracker.dto.CreateTaskRequest;
import dev.diyar68.tasktracker.dto.TaskResponse;
import dev.diyar68.tasktracker.dto.UpdateTaskRequest;
import dev.diyar68.tasktracker.entity.Task;
import dev.diyar68.tasktracker.mapper.TaskMapper;
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
    private final TaskMapper taskMapper;
    
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskMapper.toResponseList(taskService.getAllTasks());
    }

    @PostMapping
    public TaskResponse createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        Task task = taskService.createTask(createTaskRequest.getDescription());
        return taskMapper.toResponse(task);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest updateTaskRequest) {
        Task task = taskService.updateTask(id, updateTaskRequest.getDescription());
        return taskMapper.toResponse(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PatchMapping("/{id}/done")
    public TaskResponse markDone(@PathVariable Long id) {
        Task task = taskService.markDone(id);
        return taskMapper.toResponse(task);
    }

    @PatchMapping("/{id}/in-progress")
    public TaskResponse markInProgress(@PathVariable Long id) {
        Task task = taskService.markInProgress(id);
        return taskMapper.toResponse(task);
    }
}
