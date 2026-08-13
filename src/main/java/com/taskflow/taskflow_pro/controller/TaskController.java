package com.taskflow.taskflow_pro.controller;


import com.taskflow.taskflow_pro.dto.TaskRequest;
import com.taskflow.taskflow_pro.model.Task;
import com.taskflow.taskflow_pro.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/tasks")
    public Task createTask(@RequestBody @Valid TaskRequest taskRequest){

        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());
        return taskService.saveTask(task);
    }
}
