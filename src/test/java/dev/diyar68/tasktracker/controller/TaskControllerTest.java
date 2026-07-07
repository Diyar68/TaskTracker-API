package dev.diyar68.tasktracker.controller;

import dev.diyar68.tasktracker.dto.TaskResponse;
import dev.diyar68.tasktracker.entity.Task;
import dev.diyar68.tasktracker.entity.TaskStatus;
import dev.diyar68.tasktracker.exception.TaskNotFoundException;
import dev.diyar68.tasktracker.mapper.TaskMapper;
import dev.diyar68.tasktracker.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private TaskMapper taskMapper;

    @Test
    void getAllTasks_shouldReturnTasks() throws Exception {
        Task task1 = new Task("Spring lernen");
        Task task2 = new Task("Tests schreiben");

        TaskResponse response1 = new TaskResponse();
        response1.setDescription("Spring lernen");

        TaskResponse response2 = new TaskResponse();
        response2.setDescription("Tests schreiben");

        List<Task> tasks = List.of(task1, task2);
        List<TaskResponse> responses = List.of(response1, response2);

        when(taskService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.toResponseList(tasks)).thenReturn(responses);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description").value("Spring lernen"))
                .andExpect(jsonPath("$[1].description").value("Tests schreiben"));
    }

    @Test
    void getAllTasks_shouldReturnEmptyList() throws Exception {
        when(taskService.getAllTasks()).thenReturn(List.of());
        when(taskMapper.toResponseList(List.of())).thenReturn(List.of());

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void createTask_shouldReturnCreatedTask() throws Exception {
        Task task = new Task("Spring lernen");

        TaskResponse response = new TaskResponse();
        response.setDescription("Spring lernen");

        when(taskService.createTask("Spring lernen")).thenReturn(task);
        when(taskMapper.toResponse(task)).thenReturn(response);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "description": "Spring lernen"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Spring lernen"));
    }

    @Test
    void createTask_shouldReturn400_whenDescriptionIsBlank() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "description": ""
                                }
                                """))
                .andExpect(status().isBadRequest());

        verify(taskService, never()).createTask(anyString());
    }

    @Test
    void updateTask_shouldReturnUpdatedTask() throws Exception {
        Task task = new Task("Neue Beschreibung");

        TaskResponse response = new TaskResponse();
        response.setDescription("Neue Beschreibung");

        when(taskService.updateTask(1L, "Neue Beschreibung")).thenReturn(task);
        when(taskMapper.toResponse(task)).thenReturn(response);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "description": "Neue Beschreibung"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Neue Beschreibung"));
    }

    @Test
    void updateTask_shouldReturn400_whenDescriptionIsBlank() throws Exception {
        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "description": ""
                                }
                                """))
                .andExpect(status().isBadRequest());

        verify(taskService, never()).updateTask(anyLong(), anyString());
    }

    @Test
    void updateTask_shouldReturn404_whenTaskDoesNotExist() throws Exception {
        when(taskService.updateTask(eq(1L), anyString()))
                .thenThrow(new TaskNotFoundException(1L));

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "description": "Neue Beschreibung"
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void deleteTask_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk());

        verify(taskService).deleteTask(1L);
    }

    @Test
    void deleteTask_shouldReturn404_whenTaskDoesNotExist() throws Exception {
        doThrow(new TaskNotFoundException(1L))
                .when(taskService)
                .deleteTask(1L);

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void markDone_shouldReturnUpdatedTask() throws Exception {
        Task task = new Task("Spring lernen");
        task.setStatus(TaskStatus.DONE);

        TaskResponse response = new TaskResponse();
        response.setDescription("Spring lernen");
        response.setStatus(TaskStatus.DONE);

        when(taskService.markDone(1L)).thenReturn(task);
        when(taskMapper.toResponse(task)).thenReturn(response);

        mockMvc.perform(patch("/tasks/1/done"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Spring lernen"))
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    void markDone_shouldReturn404_whenTaskDoesNotExist() throws Exception {
        when(taskService.markDone(1L))
                .thenThrow(new TaskNotFoundException(1L));

        mockMvc.perform(patch("/tasks/1/done"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void markInProgress_shouldReturnUpdatedTask() throws Exception {
        Task task = new Task("Spring lernen");
        task.setStatus(TaskStatus.IN_PROGRESS);

        TaskResponse response = new TaskResponse();
        response.setDescription("Spring lernen");
        response.setStatus(TaskStatus.IN_PROGRESS);

        when(taskService.markInProgress(1L)).thenReturn(task);
        when(taskMapper.toResponse(task)).thenReturn(response);

        mockMvc.perform(patch("/tasks/1/in-progress"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Spring lernen"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void markInProgress_shouldReturn404_whenTaskDoesNotExist() throws Exception {
        when(taskService.markInProgress(1L))
                .thenThrow(new TaskNotFoundException(1L));

        mockMvc.perform(patch("/tasks/1/in-progress"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}