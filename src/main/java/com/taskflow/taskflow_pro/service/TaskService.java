package com.taskflow.taskflow_pro.service;

import com.taskflow.taskflow_pro.exception.TaskNotFoundException;
import com.taskflow.taskflow_pro.model.Task;
import com.taskflow.taskflow_pro.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskByID(long id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }
}