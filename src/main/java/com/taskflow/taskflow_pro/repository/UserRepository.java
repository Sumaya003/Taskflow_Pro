package com.taskflow.taskflow_pro.repository;

import com.taskflow.taskflow_pro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}