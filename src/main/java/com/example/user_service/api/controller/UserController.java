package com.example.user_service.api.controller;

import com.example.user_service.api.dto.request.UpdatePasswordRequest;
import com.example.user_service.api.dto.request.UpdateUserRequest;
import com.example.user_service.api.dto.response.ListUserResponse;
import com.example.user_service.api.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.user_service.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getUserById(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(userService.getUserById(token.split(" ")[1]));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }
    @GetMapping("/all")
    public ResponseEntity<ListUserResponse> getAllUsers() {
        return ResponseEntity.ok().body(
                new ListUserResponse(userService.getAllUsers())
        );
    }
    @DeleteMapping("/delete")
    public ResponseEntity<UserResponse> deleteUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(userService.deleteUser(token.split(" ")[1]));
    }
    @PostMapping("/update/data")
    public ResponseEntity<UserResponse> updateUser(
            @RequestBody UpdateUserRequest updateUserRequest,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok().body(userService.updateUser(updateUserRequest, token.split(" ")[1]));
    }
    @PostMapping("/update/password")
    public ResponseEntity<UserResponse> updatePassword(
            @RequestBody UpdatePasswordRequest updatePasswordRequest,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok().body(userService.updatePassword(updatePasswordRequest, token.split(" ")[1]));
    }
}
