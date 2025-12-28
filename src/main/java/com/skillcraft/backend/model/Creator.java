package com.skillcraft.backend.model; // Adjust this to your actual package name!

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "creators")
@Data // Lombok annotation to generate getters, setters, toString, etc.
public class Creator {
    @Id
    @GeneratedValue
    private UUID id; // Using UUID for unique IDs across systems

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    private String displayName;

    @Column(unique = true)
    private String storeSlug; // Used for the public store URL (e.g., /stores/johndoe)

    private String profileBio;

    @CreationTimestamp
    private Instant createdAt;
}