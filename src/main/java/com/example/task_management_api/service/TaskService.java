package com.example.task_management_api.service;

import com.example.task_management_api.model.Task;
import com.example.task_management_api.model.User;
import com.example.task_management_api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task, User user) {
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(User user) {
        return taskRepository.findByUser(user);
    }

    public Optional<Task> getTaskById(Long id, User user) {
        return taskRepository.findById(id)
                .filter(task -> task.getUser().getId().equals(user.getId()));
    }

    public Optional<Task> updateTask(Long id, Task updatedTask, User user) {
        return getTaskById(id, user).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            return taskRepository.save(task);
        });
    }

    public boolean deleteTask(Long id, User user) {
        return getTaskById(id, user).map(task -> {
            taskRepository.delete(task);
            return true;
        }).orElse(false);
    }
}
