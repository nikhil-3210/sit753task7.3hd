package com.example.task_management_api.controller;

import com.example.task_management_api.dto.LoginRequest;
import com.example.task_management_api.dto.LoginResponse;
import com.example.task_management_api.dto.RegisterRequest;
import com.example.task_management_api.model.User;
import com.example.task_management_api.service.UserService;
import com.example.task_management_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.register(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userService.authenticate(request.getUsername(), request.getPassword());

        if (userOpt.isPresent()) {
            String token = jwtUtil.generateToken(userOpt.get().getUsername());
            LoginResponse response = new LoginResponse(token);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
