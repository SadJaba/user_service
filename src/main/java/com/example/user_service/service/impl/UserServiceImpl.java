package com.example.user_service.service.impl;

import com.example.user_service.api.dto.request.UpdatePasswordRequest;
import com.example.user_service.api.dto.request.UpdateUserRequest;
import com.example.user_service.api.dto.response.UserResponse;
import com.example.user_service.domain.model.User;
import com.example.user_service.domain.repository.UserRepository;
import com.example.user_service.service.UserService;
import com.example.user_service.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserResponse getUserById(String token) {
        return getUser(token).toDto().build();
    }

    @Override
    public UserResponse getUserById(UUID id) {
        return getUser(id).toDto().build();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(a -> a.toDto().build())
                .toList();
    }

    @Override
    public UserResponse deleteUser(String token) {
        User user = getUser(token);
        userRepository.delete(user);
        return user.toDto().build();
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest updateUserRequest, String token) {
        User user = getUser(token);
        user.setFirstName(updateUserRequest.firstName());
        user.setLastName(updateUserRequest.lastName());
        user.setEmail(updateUserRequest.email());
        return userRepository.save(user).toDto().build();
    }

    @Override
    public UserResponse updatePassword(UpdatePasswordRequest updatePasswordRequest, String token) {
        User user = getUser(token);
        user.setPassword(
                passwordEncoder.encode(updatePasswordRequest.password())
        );
        return userRepository.save(user).toDto().build();
    }

    private User getUser(String token) {
        UUID id = jwtService.extractUserId(token);
        return getUser(id);
    }
    private User getUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("User not found: " + id));
    }
}
