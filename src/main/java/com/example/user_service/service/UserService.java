package com.example.user_service.service;

import com.example.user_service.api.dto.request.UpdatePasswordRequest;
import com.example.user_service.api.dto.request.UpdateUserRequest;
import com.example.user_service.api.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse getUserById(String token);

    UserResponse getUserById(UUID id);

    List<UserResponse> getAllUsers();

    UserResponse deleteUser(String token);

    UserResponse updateUser(UpdateUserRequest updateUserRequest, String token);

    UserResponse updatePassword(UpdatePasswordRequest updatePasswordRequest, String token);

}
