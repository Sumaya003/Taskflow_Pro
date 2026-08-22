package com.taskflow.taskflow_pro.controller;

import com.taskflow.taskflow_pro.dto.LoginRequest;
import com.taskflow.taskflow_pro.dto.LoginResponse;
import com.taskflow.taskflow_pro.dto.UserRequest;
import com.taskflow.taskflow_pro.dto.UserResponse;
import com.taskflow.taskflow_pro.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
