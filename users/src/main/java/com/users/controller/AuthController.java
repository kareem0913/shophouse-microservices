package com.users.controller;

import com.users.model.dto.auth.AdminCreationRequest;
import com.users.model.dto.auth.JwtAuthenticationResponse;
import com.users.model.dto.auth.LoginRequest;
import com.users.model.dto.auth.UserRegistrationRequest;
import com.users.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Authentication and user management endpoints")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Username already exists")
    })
    public JwtAuthenticationResponse httpRegisterUser(
            @Valid @RequestBody UserRegistrationRequest request) {

        log.info("User registration attempt for username: {}", request.getUsername());
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public JwtAuthenticationResponse httpAuthenticateUser(
            @Valid @RequestBody LoginRequest request) {

        log.info("Login attempt for username: {}", request.getUsername());
        return authService.authenticateUser(request);
    }

    @PostMapping("/admin/create")
    @Operation(summary = "Create admin user",
            description = "Create a new admin user. Requires admin creation key, you can use this key: SUPER_SECRET_ADMIN_KEY")
    @SecurityRequirement(name = "admin-creation-key")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing admin creation key"),
            @ApiResponse(responseCode = "409", description = "Username already exists")
    })
    public JwtAuthenticationResponse httpCreateAdmin(
            @Parameter(description = "Admin creation key", required = true,
                    example = "SUPER_SECRET_ADMIN_KEY")
            @Valid @RequestBody AdminCreationRequest request) {

        log.info("Admin creation attempt for username: {}", request.getUsername());
        return authService.createAdmin(request);
    }
}