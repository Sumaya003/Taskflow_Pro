package com.taskflow.taskflow_pro.dto;

import com.taskflow.taskflow_pro.model.Priority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private UserResponse user;
}