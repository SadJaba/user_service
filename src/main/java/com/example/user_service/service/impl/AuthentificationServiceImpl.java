package com.example.user_service.service.impl;

import com.example.user_service.api.dto.request.AuthenticationRequest;
import com.example.user_service.api.dto.request.CreateUserRequest;
import com.example.user_service.api.dto.response.UserDetailsResponse;
import com.example.user_service.api.dto.response.UserResponse;
import com.example.user_service.domain.model.User;
import com.example.user_service.service.AuthenticationService;
import com.example.user_service.domain.repository.UserRepository;
import com.example.user_service.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthentificationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public UserResponse signUp(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.email())) {
            return authenticate(new AuthenticationRequest(createUserRequest.email(), createUserRequest.password()));
        }
        User user = User.builder()
                .firstName(createUserRequest.firstName())
                .lastName(createUserRequest.lastName())
                .email(createUserRequest.email())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .build();
        return userRepository.save(user).toDto()
                .jwtToken(jwtService.generateToken(user.getEmail(), user.getId()))
                .build();
    }

    @Override
    public UserResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("User not found"));
        return user.toDto()
                .jwtToken(jwtService.generateToken(user.getEmail(), user.getId()))
                .build();
    }

    @Override
    public UserDetailsResponse isTokenValid(String token) {
        try {
            String email = jwtService.extractUsername(token);
            UUID id = jwtService.extractUserId(token);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            if (jwtService.isTokenValid(token, userDetails)) {
                return new UserDetailsResponse(
                        id,
                        userDetails.getUsername(),
                        userDetails.getAuthorities()
                );
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
