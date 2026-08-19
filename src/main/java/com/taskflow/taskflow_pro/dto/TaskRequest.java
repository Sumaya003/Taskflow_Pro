package com.taskflow.taskflow_pro.dto;

import com.taskflow.taskflow_pro.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Priority priority;

    private Long userId;
}
