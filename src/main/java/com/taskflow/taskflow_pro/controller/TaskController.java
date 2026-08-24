package com.taskflow.taskflow_pro.controller;


import com.taskflow.taskflow_pro.dto.TaskRequest;
import com.taskflow.taskflow_pro.dto.TaskResponse;
import com.taskflow.taskflow_pro.model.Priority;
import com.taskflow.taskflow_pro.model.Task;
import com.taskflow.taskflow_pro.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/api/hello")
    public String hello(){
        return taskService.getHelloMessage();
    }

    @PostMapping("/api/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(
            @Valid @RequestBody TaskRequest taskRequest,
            Authentication authentication) {

        String email = authentication.getName();

        return taskService.createTask(taskRequest, email);
    }

    @GetMapping("/api/tasks")
    public Page<TaskResponse> getAllTasks(
            Pageable pageable,
            Authentication authentication) {

        return taskService.getAllTasks(
                pageable,
                authentication.getName()
        );
    }

    @GetMapping("/api/tasks/{id}")
    public TaskResponse getTaskById(
            @PathVariable Long id,
            Authentication authentication) {

        return taskService.getTaskById(
                id,
                authentication.getName()
        );
    }

    @GetMapping("/api/tasks/priority/{priority}")
    public List<TaskResponse> getTasksByPriority(
            @PathVariable Priority priority,
            Authentication authentication) {

        return taskService.getTasksByPriority(
                priority,
                authentication.getName()
        );
    }

    @GetMapping("/api/tasks/search")
    public List<TaskResponse> getTasksByTitle(@RequestParam String title, Authentication authentication){
        return taskService.searchTasks(title,authentication.getName());
    }

    @PutMapping("/api/tasks/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest taskRequest,
            Authentication authentication) {

        return taskService.updateTask(
                id,
                taskRequest,
                authentication.getName()
        );
    }

    @DeleteMapping("/api/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(
            @PathVariable Long id,
            Authentication authentication) {

        taskService.deleteTask(
                id,
                authentication.getName()
        );
    }
}
