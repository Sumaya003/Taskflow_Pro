package com.taskflow.taskflow_pro.service;

import com.taskflow.taskflow_pro.dto.PageResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
        log.info("Task created with id={} for user={}",
                savedTask.getId(), email);
        return mapToResponse(savedTask);
    }

    public PageResponse<TaskResponse> getAllTasks(
            Pageable pageable,
            String email) {

        Page<TaskResponse> page = taskRepository
                .findByUserEmailAndDeletedFalse(email, pageable)
                .map(this::mapToResponse);

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    public TaskResponse getTaskById(Long id, String email) {

        Task task = taskRepository.findByIdAndUserEmailAndDeletedFalse(id, email)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        return mapToResponse(task);
    }

    public TaskResponse updateTask(
            Long id,
            TaskRequest taskRequest,
            String email) {

        Task task = taskRepository.findByIdAndUserEmailAndDeletedFalse(id, email)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());

        Task updatedTask = taskRepository.save(task);
        log.info("Task updated with id={} by user={}",
                updatedTask.getId(), email);
        return mapToResponse(updatedTask);
    }

    public void deleteTask(Long id, String email) {

        Task task = taskRepository.findByIdAndUserEmailAndDeletedFalse(id, email)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        task.setDeleted(true);
        log.info("Task soft deleted with id={} by user={}",
                task.getId(), email);
        taskRepository.save(task);
    }

    public List<TaskResponse> getTasksByPriority(Priority priority, String email) {
        return taskRepository.findByPriorityAndUserEmailAndDeletedFalse(
                        priority,
                        email
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<TaskResponse> searchTasks(String title, String email) {
        return taskRepository.findByTitleContainingIgnoreCaseAndUserEmailAndDeletedFalse(
                        title,
                        email
                )
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
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());

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