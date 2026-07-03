package dev.diyar68.tasktracker.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import dev.diyar68.tasktracker.dto.CreateTaskRequest;
import dev.diyar68.tasktracker.dto.TaskResponse;
import dev.diyar68.tasktracker.entity.Task;

@Component
public class TaskMapper {

    public Task toEntity(CreateTaskRequest request) {
        return new Task(request.getDescription());
    }

    public TaskResponse toResponse(Task task) {
        TaskResponse response = new TaskResponse();

        response.setId(task.getId());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());

        return response;
    }

    public List<TaskResponse> toResponseList(List<Task> tasks) {
        List<TaskResponse> responses = new ArrayList<>();

        for (Task task : tasks) {
            responses.add(toResponse(task));
        }

        return responses;
    }
}