package com.example.Task.Management.controller;

import com.example.Task.Management.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    public TaskController() {
        // Adding some sample tasks for testing
        tasks.add(new Task(nextId++, "Finish Assignment", "Complete the Spring Boot task API assignment", false, "HIGH",
                "2026-02-15"));
        tasks.add(new Task(nextId++, "Buy Groceries", "Milk, Eggs, Bread", true, "MEDIUM", "2026-02-10"));
        tasks.add(new Task(nextId++, "Exercise", "30 minutes run", false, "LOW", "2026-02-12"));
    }

    // GET /api/tasks - Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    // GET /api/tasks/{taskId} - Get task by ID
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        return tasks.stream()
                .filter(task -> task.getTaskId().equals(taskId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/tasks/status?completed={true/false} - Get tasks by completion status
    @GetMapping("/status")
    public List<Task> getTasksByStatus(@RequestParam boolean completed) {
        return tasks.stream()
                .filter(task -> task.isCompleted() == completed)
                .collect(Collectors.toList());
    }

    // GET /api/tasks/priority/{priority} - Get tasks by priority
    @GetMapping("/priority/{priority}")
    public List<Task> getTasksByPriority(@PathVariable String priority) {
        return tasks.stream()
                .filter(task -> task.getPriority().equalsIgnoreCase(priority))
                .collect(Collectors.toList());
    }

    // POST /api/tasks - Create new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        task.setTaskId(nextId++);
        tasks.add(task);
        return ResponseEntity.ok(task);
    }

    // PUT /api/tasks/{taskId} - Update task
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getTaskId().equals(taskId)) {
                updatedTask.setTaskId(taskId);
                tasks.set(i, updatedTask);
                return ResponseEntity.ok(updatedTask);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH /api/tasks/{taskId}/complete - Mark task as completed
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long taskId) {
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                task.setCompleted(true);
                return ResponseEntity.ok(task);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/tasks/{taskId} - Delete task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        boolean removed = tasks.removeIf(task -> task.getTaskId().equals(taskId));
        if (removed) {
            return ResponseEntity.ok("Task deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
