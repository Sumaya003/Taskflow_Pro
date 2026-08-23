package com.taskflow.taskflow_pro.service;

import com.taskflow.taskflow_pro.dto.TaskRequest;
import com.taskflow.taskflow_pro.dto.TaskResponse;
import com.taskflow.taskflow_pro.dto.UserResponse;
import com.taskflow.taskflow_pro.exception.TaskNotFoundException;
import com.taskflow.taskflow_pro.exception.UserNotFoundException;
import com.taskflow.taskflow_pro.model.Priority;
import com.taskflow.taskflow_pro.model.Task;
import com.taskflow.taskflow_pro.model.User;
import com.taskflow.taskflow_pro.repository.TaskRepository;
import com.taskflow.taskflow_pro.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public String getHelloMessage() {
        return "Hello from TaskFlow Pro";
    }

    public TaskResponse createTask(TaskRequest taskRequest, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());

        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return mapToResponse(savedTask);
    }

    public Page<TaskResponse> getAllTasks(
            Pageable pageable,
            String email) {

        return taskRepository
                .findByUserEmail(email, pageable)
                .map(this::mapToResponse);
    }

    public TaskResponse getTaskById(Long id, String email) {

        Task task = taskRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        return mapToResponse(task);
    }

    public TaskResponse updateTask(
            Long id,
            TaskRequest taskRequest,
            String email) {

        Task task = taskRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());

        Task updatedTask = taskRepository.save(task);

        return mapToResponse(updatedTask);
    }

    public void deleteTask(Long id, String email) {

        Task task = taskRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

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

        if (task.getUser() != null) {
            UserResponse userResponse = new UserResponse();

            userResponse.setId(task.getUser().getId());
            userResponse.setName(task.getUser().getName());
            userResponse.setEmail(task.getUser().getEmail());

            response.setUser(userResponse);
        }

        return response;
    }
}