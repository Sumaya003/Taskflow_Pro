package com.taskflow.taskflow_pro.dto;

import com.taskflow.taskflow_pro.model.Priority;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    @NotBlank
    private String title;
    private String description;
    private Priority priority;
}
