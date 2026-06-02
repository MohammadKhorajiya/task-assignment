package com.taskmanager.taskassignment.controller;

import com.taskmanager.taskassignment.model.Priority;
import com.taskmanager.taskassignment.model.Task;
import com.taskmanager.taskassignment.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks_users")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<String> createTask(@Valid @RequestBody Task task) {
        return new ResponseEntity<>(taskService.createTasks(task), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Task>> getAllTasksByPaginated(@RequestParam (defaultValue="0")int page, @RequestParam (defaultValue ="10")int size)
    {
        return ResponseEntity.ok(taskService.findTasksPaginated(page,size));
    }

    @GetMapping(value = "/find-all-search",produces = "application/json")
    public ResponseEntity<Page<Task>> searchTasks(@RequestParam(required = false)Boolean status,@RequestParam(required = false)Priority priority,@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size)
    {
        return ResponseEntity.ok(taskService.searchTasks(status,priority,page,size));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @GetMapping("/search-title")
    public ResponseEntity<List<Task>> searchTasksByTitle(@RequestParam String title) {
        return new ResponseEntity<>(taskService.searchTaskByTitle(title), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task updatedTasks) {
        return new ResponseEntity<>(taskService.updateTask(id, updatedTasks), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable String id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getAllTasksForAdmin() {
        return new ResponseEntity<>(taskService.getAll(), HttpStatus.OK);
    }
}