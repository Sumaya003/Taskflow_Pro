package com.taskflow.taskflow_pro.service;

import com.taskflow.taskflow_pro.dto.TaskRequest;
import com.taskflow.taskflow_pro.exception.TaskNotFoundException;
import com.taskflow.taskflow_pro.model.Task;
import com.taskflow.taskflow_pro.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Task getTaskByID(long id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public Task updateTask(Long id, TaskRequest  taskRequest) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());

        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
    }
}