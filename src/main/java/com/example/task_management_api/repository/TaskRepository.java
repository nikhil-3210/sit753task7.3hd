package com.example.task_management_api.repository;

import com.example.task_management_api.model.Task;
import com.example.task_management_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
