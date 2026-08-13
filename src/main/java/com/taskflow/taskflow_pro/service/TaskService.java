package com.taskflow.taskflow_pro.service;

import com.taskflow.taskflow_pro.model.Task;
import com.taskflow.taskflow_pro.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public String getHelloMessage() {
        return "Hello from TaskFlow Pro";
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
}