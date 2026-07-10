package dev.diyar68.tasktracker.service;

import dev.diyar68.tasktracker.entity.Task;
import dev.diyar68.tasktracker.entity.TaskStatus;
import dev.diyar68.tasktracker.exception.TaskNotFoundException;
import dev.diyar68.tasktracker.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_shouldSaveTask() {
        Task task = new Task("Spring lernen");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.createTask("Spring lernen");

        assertEquals("Spring lernen", result.getDescription());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void deleteTask_shouldDeleteTask_whenTaskExists() {
        Long id = 1L;

        when(taskRepository.existsById(id)).thenReturn(true);

        taskService.deleteTask(id);

        verify(taskRepository).deleteById(id);
    }

    @Test
    void deleteTask_shouldThrowException_whenTaskDoesNotExist() {
        Long id = 1L;

        when(taskRepository.existsById(id)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(id));

        verify(taskRepository, never()).deleteById(id);
    }

    @Test
    void deleteTask_shouldThrowException_whenIdIsNull() {
        
        assertThrows(
            IllegalArgumentException.class,
            () -> taskService.deleteTask(null)
        );

        verify(taskRepository, never()).existsById(any());
        verify(taskRepository, never()).deleteById(any());
    }

    @Test
    void updateTask_shouldUpdateDescription_whenTaskExists() {
        Long id = 1L;
        Task existingTask = new Task("Old description");

        when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task result = taskService.updateTask(id, "New description");

        assertEquals("New description", result.getDescription());
        verify(taskRepository).save(existingTask);
    }

    @Test
    void updateTask_shouldThrowException_whenTaskDoesNotExist() {
        Long id = 1L;

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(id, "New description"));

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateTask_shouldThrowException_whenIdIsNull() {
        assertThrows(
            IllegalArgumentException.class,
            () -> taskService.updateTask(null, "New description")
        );

        verify(taskRepository, never()).findById(any());
        verify(taskRepository, never()).save(any());
    }

    @Test
    void markDone_shouldSetStatusToDone() {
        Long id = 1L;
        Task task = new Task("Test task");

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.markDone(id);

        assertEquals(TaskStatus.DONE, result.getStatus());
        verify(taskRepository).save(task);
    }

    @Test
    void markDone_shouldThrowException_whenTaskDoesNotExist() {
        Long id = 1L;

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.markDone(id));

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void markInProgress_shouldSetStatusToInProgress() {
        Long id = 1L;
        Task task = new Task("Test task");

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.markInProgress(id);

        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
        verify(taskRepository).save(task);
    }

    @Test
    void markInProgress_shouldThrowException_whenTaskDoesNotExist() {
        Long id = 1L;

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.markInProgress(id));

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void getAllTasks_shouldGiveAllTasks() {
        Task task1 = new Task("Spring lernen");
        Task task2 = new Task("Tests schreiben");

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        assertEquals("Spring lernen", result.get(0).getDescription());
        assertEquals("Tests schreiben", result.get(1).getDescription());

        verify(taskRepository).findAll();
    }

    @Test
    void getTasksByStatus_shouldGiveTasksByDone() {
        Task task = new Task("Fertiger Task");
        task.setStatus(TaskStatus.DONE);
        
        when(taskRepository.findByStatus(TaskStatus.DONE))
                .thenReturn(List.of(task));

        List<Task> result = taskService.getTasksByStatus(TaskStatus.DONE);

        assertEquals(1, result.size());
        assertEquals(TaskStatus.DONE, result.get(0).getStatus());

        verify(taskRepository).findByStatus(TaskStatus.DONE);
    }

    @Test
    void getTasksByStatus_shouldGiveTasksByInProgress() {
        Task task = new Task("Aktiver Task");
        task.setStatus(TaskStatus.IN_PROGRESS);

        when(taskRepository.findByStatus(TaskStatus.IN_PROGRESS))
            .thenReturn(List.of(task));

        List<Task> result = taskService.getTasksByStatus(TaskStatus.IN_PROGRESS);

        assertEquals(1, result.size());
        assertEquals(TaskStatus.IN_PROGRESS, result.get(0).getStatus());

        verify(taskRepository).findByStatus(TaskStatus.IN_PROGRESS);
    }

    @Test
    void getTasksByStatus_shouldThrowException_whenStatusIsNull() {
        assertThrows(IllegalArgumentException.class, () -> taskService.getTasksByStatus(null));
    }
}