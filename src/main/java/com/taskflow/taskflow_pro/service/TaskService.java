package com.taskflow.taskflow_pro.service;

import com.taskflow.taskflow_pro.dto.TaskRequest;
import com.taskflow.taskflow_pro.dto.TaskResponse;
import com.taskflow.taskflow_pro.exception.TaskNotFoundException;
import com.taskflow.taskflow_pro.model.Priority;
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

    public TaskResponse createTask(TaskRequest taskRequest) {

        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());

        Task savedTask = taskRepository.save(task);
        return mapToResponse(savedTask);
    }

    public Page<TaskResponse> getAllTasks(Pageable pageable) {

        return taskRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public TaskResponse getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        return mapToResponse(task);
    }

    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());

        Task updatedTask = taskRepository.save(task);

        return mapToResponse(updatedTask);
    }

    public void deleteTask(Long id){
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

    public List<TaskResponse> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<TaskResponse> searchTasks(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TaskResponse mapToResponse(Task task) {
        TaskResponse response = new TaskResponse();

        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setPriority(task.getPriority());

        return response;
    }
}