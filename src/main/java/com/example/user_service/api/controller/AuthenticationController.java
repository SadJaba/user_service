package com.example.user_service.api.controller;

import com.example.user_service.api.dto.request.AuthenticationRequest;
import com.example.user_service.api.dto.request.CreateUserRequest;
import com.example.user_service.api.dto.response.UserDetailsResponse;
import com.example.user_service.api.dto.response.UserResponse;
import com.example.user_service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerClient(@RequestBody CreateUserRequest registerUserDto) {
        var user = authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + user.jwtToken())
                .body(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var user = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + user.jwtToken())
                .body(user);
    }

    @GetMapping("/verify")
    public ResponseEntity<UserDetailsResponse> verify(@RequestParam(value = "token") String token) {
        var res = authenticationService.isTokenValid(token);
        return ResponseEntity.ok().body(res);
    }
}
