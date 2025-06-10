package com.example.beehiveproject.controller;


import com.example.beehiveproject.dto.request.LoginRequest;
import com.example.beehiveproject.dto.request.RegisterRequest;
import com.example.beehiveproject.dto.response.LoginResponse;
import com.example.beehiveproject.dto.response.UserResponse;
import com.example.beehiveproject.entity.User;
import com.example.beehiveproject.repository.UserRepository;
import com.example.beehiveproject.service.AuthService;
import com.example.beehiveproject.dto.request.ColonyCreateRequest;
import com.example.beehiveproject.dto.response.ColonyResponse;
import com.example.beehiveproject.service.ColonyService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is our REST controller for handling authentication-related endpoints.
 * It maps HTTP requests to service methods.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final ColonyService colonyService;

    // Constructor injection for the AuthService
    public AuthController(AuthService authService, UserRepository userRepository, ColonyService colonyService) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.colonyService = colonyService;
    }

    /**
     * POST /auth/register
     * Handles user registration.
     * Takes a JSON body (RegisterRequest) and returns basic user info.
     */
    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    /**
     * POST /auth/login
     * Handles user login.
     * Takes email & password and returns a JWT token in LoginResponse.
     */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * GET /auth/me
     * Returns the current user's info based on JWT token.
     * The token must be sent in the Authorization header as: Bearer <token>
     */
    @GetMapping("/me")
    public UserResponse getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // Remove "Bearer "
        return authService.getCurrentUser(token);
    }

    @GetMapping("/test")
    public Optional<User> testFunc(@RequestParam String id) {
        return userRepository.findById(id);
    }

    @PostMapping("/colonies")
    public ColonyResponse createColony(@RequestBody ColonyCreateRequest request) {
        return colonyService.createColony(request);
    }

    /**
     * POST /auth/reset-password
     * Şifre sıfırlama. Body'de email bekler. Geçici şifre response olarak döner.
     */
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody java.util.Map<String, String> body) {
        String email = body.get("email");
        return authService.resetPassword(email);
    }

    /**
     * POST /auth/register-admin
     * Admin kaydı için endpoint.
     */


    // @GetMapping
    // public List<ColonyResponse> getAllColonies() {
    //     return colonyService.getAllColonies();
    // }

}


// In REST, everything is treated as a resource (like a user, product, etc.).
// Each resource is uniquely identified using a URI (Uniform Resource Identifier).

// URIs should refer to "things", not actions.
// ✅ Good: /users      ❌ Bad: /getUsers

// Use plural nouns for resource names.
// ✅ /books   ✅ /students   ❌ /book

// Use nested resources to show relationships.
// Example: /users/123/orders → orders that belong to user 123

// Avoid using file extensions in URIs.
// ❌ /users.json   ✅ /users

// Use path parameters for resource IDs, not query strings.
// ✅ /users/123    ❌ /users?id=123

