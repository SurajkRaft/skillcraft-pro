package com.skillcraft.backend.repository; // Adjust to your package

import com.skillcraft.backend.model.Creator; // Adjust to your package
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, UUID> {
    // This method will be auto-implemented by Spring Data JPA
    Optional<Creator> findByEmail(String email);
}