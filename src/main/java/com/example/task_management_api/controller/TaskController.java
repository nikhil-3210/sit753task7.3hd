package com.example.task_management_api.controller;

import com.example.task_management_api.model.Task;
import com.example.task_management_api.model.User;
import com.example.task_management_api.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task,
                                           @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.createTask(task, user));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getAllTasks(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id,
                                     @AuthenticationPrincipal User user) {
        Optional<Task> task = taskService.getTaskById(id, user);

        if (task.isPresent()) {
            return ResponseEntity.ok(task.get());
        } else {
            return ResponseEntity.status(404).body("Task not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id,
                                        @RequestBody Task task,
                                        @AuthenticationPrincipal User user) {
        Optional<Task> updatedTask = taskService.updateTask(id, task, user);

        if (updatedTask.isPresent()) {
            return ResponseEntity.ok(updatedTask.get());
        } else {
            return ResponseEntity.status(404).body("Task not found or unauthorized");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id,
                                        @AuthenticationPrincipal User user) {
        boolean deleted = taskService.deleteTask(id, user);
        return deleted ?
                ResponseEntity.ok("Task deleted") :
                ResponseEntity.status(404).body("Task not found or unauthorized");
    }
}
