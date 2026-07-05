package dev.diyar68.tasktracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.diyar68.tasktracker.entity.TaskStatus;
import dev.diyar68.tasktracker.repository.TaskRepository;
import dev.diyar68.tasktracker.entity.Task;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    public Task createTask(String description) {
        return taskRepository.save(new Task(description));
    }

    public void deleteTask(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("This task does not exist");
        }

        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id, String description) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isEmpty()) {
            throw new IllegalArgumentException("This task does not exist");
        }

        Task task = taskOptional.get();

        task.setDescription(description);

        return taskRepository.save(task);
    }

    private Task changeStatus(Long id, TaskStatus status) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isEmpty()) {
            throw new IllegalArgumentException("This task does not exist");
        }

        Task task = taskOptional.get();

        task.setStatus(status);

        return taskRepository.save(task);
    }

    public Task markInProgress(Long id) {
        return changeStatus(id, TaskStatus.IN_PROGRESS);
    }

    public Task markDone(Long id) {
        return changeStatus(id, TaskStatus.DONE);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByStatus(TaskStatus status) {

        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        
        return taskRepository.findByStatus(status);
    }
}
