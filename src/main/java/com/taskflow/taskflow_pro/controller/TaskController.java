package com.taskflow.taskflow_pro.controller;


import com.taskflow.taskflow_pro.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
