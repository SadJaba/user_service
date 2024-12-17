package com.example.user_service.service;

import com.example.user_service.api.dto.request.AuthenticationRequest;
import com.example.user_service.api.dto.request.CreateUserRequest;
import com.example.user_service.api.dto.response.UserDetailsResponse;
import com.example.user_service.api.dto.response.UserResponse;

public interface AuthenticationService {
    UserResponse signUp(CreateUserRequest createUserRequest);

    UserResponse authenticate(AuthenticationRequest request);

    UserDetailsResponse isTokenValid(String token);
}
