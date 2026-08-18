package com.taskflow.taskflow_pro.controller;


import com.taskflow.taskflow_pro.dto.TaskRequest;
import com.taskflow.taskflow_pro.dto.TaskResponse;
import com.taskflow.taskflow_pro.model.Task;
import com.taskflow.taskflow_pro.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/tasks")
    public TaskResponse createTask(@RequestBody @Valid TaskRequest taskRequest){

        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());
        return taskService.saveTask(task);
    }

    @GetMapping("/api/tasks")
    public Page<TaskResponse> getAllTasks(Pageable pageable){
        return taskService.getAllTasks(pageable);
    }

    @GetMapping("/api/tasks/{id}")
    public TaskResponse getTaskById(@PathVariable long id){
        return taskService.getTaskById(id);
    }

    @PutMapping("/api/tasks/{id}")
    public TaskResponse updateTask(@PathVariable long id, @RequestBody @Valid TaskRequest taskRequest){
        return taskService.updateTask(id, taskRequest);
    }

    @DeleteMapping("/api/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id){
        taskService.deleteTask(id);
    }
}
