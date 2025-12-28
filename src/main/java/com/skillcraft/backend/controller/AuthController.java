package com.skillcraft.backend.controller;

import com.skillcraft.backend.dto.SignupRequest;
import com.skillcraft.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // Constructor injection (Spring will provide the AuthService)
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerCreator(@Valid @RequestBody SignupRequest signupRequest) {
        // We will implement this logic in the service next
        return authService.registerCreator(signupRequest);
    }
}