package com.taskflow.taskflow_pro.service;

import com.taskflow.taskflow_pro.dto.UserRequest;
import com.taskflow.taskflow_pro.dto.UserResponse;
import com.taskflow.taskflow_pro.model.User;
import com.taskflow.taskflow_pro.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());

        return response;
    }
}