package com.taskflow.taskflow_pro.repository;

import com.taskflow.taskflow_pro.model.Priority;
import com.taskflow.taskflow_pro.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndUserEmailAndDeletedFalse(
            Long id,
            String email
    );

    Page<Task> findByUserEmailAndDeletedFalse(
            String email,
            Pageable pageable
    );

    List<Task> findByPriorityAndUserEmailAndDeletedFalse(
            Priority priority,
            String email
    );

    List<Task> findByTitleContainingIgnoreCaseAndUserEmailAndDeletedFalse(
            String title,
            String email
    );
}