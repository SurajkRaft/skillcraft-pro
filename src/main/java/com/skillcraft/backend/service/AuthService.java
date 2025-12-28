package com.skillcraft.backend.service;

import com.skillcraft.backend.dto.LoginRequest;
import com.skillcraft.backend.dto.LoginResponse;
import com.skillcraft.backend.dto.SignupRequest;
import com.skillcraft.backend.model.Creator;
import com.skillcraft.backend.repository.CreatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Lombok generates a constructor for final fields (creatorRepository)
public class AuthService {

    private final CreatorRepository creatorRepository;
    private final JwtService jwtService; // <-- Add this line
    // BCrypt is the industry standard for password hashing
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ResponseEntity<?> registerCreator(SignupRequest signupRequest) {
        // 1. Business Rule: Check if email is already registered
        Optional<Creator> existingCreator = creatorRepository.findByEmail(signupRequest.getEmail());
        if (existingCreator.isPresent()) {
            // Return a structured error response
            Map<String, String> error = new HashMap<>();
            error.put("message", "A creator with this email already exists.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        // 2. Business Rule & Security: Encrypt the raw password
        String encryptedPassword = passwordEncoder.encode(signupRequest.getPassword());

        // 3. Create and populate the new Creator entity
        Creator newCreator = new Creator();
        newCreator.setEmail(signupRequest.getEmail());
        newCreator.setEncryptedPassword(encryptedPassword);
        newCreator.setDisplayName(signupRequest.getDisplayName());
        newCreator.setStoreSlug(signupRequest.getStoreSlug()); // Could be null
        newCreator.setProfileBio(signupRequest.getProfileBio()); // Could be null

        // 4. Save to the database via the repository
        Creator savedCreator = creatorRepository.save(newCreator);

        // 5. Prepare a success response (exclude the encrypted password)
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Creator registered successfully!");
        response.put("creatorId", savedCreator.getId());
        response.put("email", savedCreator.getEmail());
        response.put("storeSlug", savedCreator.getStoreSlug());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    public ResponseEntity<?> authenticateCreator(LoginRequest loginRequest) {
        // 1. Find creator by email
        Optional<Creator> creatorOptional = creatorRepository.findByEmail(loginRequest.getEmail());
        if (creatorOptional.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid email or password.");
            // Use UNAUTHORIZED (401) for failed authentication
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        Creator creator = creatorOptional.get();

        // 2. Validate the provided password against the encrypted one
        if (!passwordEncoder.matches(loginRequest.getPassword(), creator.getEncryptedPassword())) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid email or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        // 3. Generate a JWT token for the authenticated creator
        String jwtToken = jwtService.generateToken(creator.getEmail());

        // 4. Return the token and user info in the response
        LoginResponse response = new LoginResponse(
                jwtToken,
                "Login successful",
                creator.getEmail()
        );
        return ResponseEntity.ok(response);
    }

}