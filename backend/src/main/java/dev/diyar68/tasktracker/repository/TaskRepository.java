package dev.diyar68.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.diyar68.tasktracker.entity.Task;
import dev.diyar68.tasktracker.entity.TaskStatus;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByStatus(TaskStatus status);
}
