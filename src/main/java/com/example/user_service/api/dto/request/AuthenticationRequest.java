package com.example.user_service.api.dto.request;

public record AuthenticationRequest(
        String email,
        String password
) {
}
