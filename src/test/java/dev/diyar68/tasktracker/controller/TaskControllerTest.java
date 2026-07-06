package dev.diyar68.tasktracker.controller;

import dev.diyar68.tasktracker.dto.TaskResponse;
import dev.diyar68.tasktracker.entity.Task;
import dev.diyar68.tasktracker.mapper.TaskMapper;
import dev.diyar68.tasktracker.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}