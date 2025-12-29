package com.skillcraft.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Display name is mandatory")
    private String displayName;

    private String storeSlug; // Optional, for the public store URL
    private String profileBio; // Optional
}