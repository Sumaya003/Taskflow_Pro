package com.taskflow.taskflow_pro.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
