package com.taskflow.taskflow_pro.repository;

import com.taskflow.taskflow_pro.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}