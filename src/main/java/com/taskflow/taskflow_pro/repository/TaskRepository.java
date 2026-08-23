package com.taskflow.taskflow_pro.repository;

import com.taskflow.taskflow_pro.model.Priority;
import com.taskflow.taskflow_pro.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByPriority(Priority priority);
    List<Task> findByTitleContainingIgnoreCase(String title);
    Optional<Task> findByIdAndUserEmail(Long id, String email);
    Page<Task> findByUserEmail(String email, Pageable pageable);
}