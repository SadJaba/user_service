package com.example.user_service.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserRequest(
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name")String lastName,
        String email,
        String password
){
}
