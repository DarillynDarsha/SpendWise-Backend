package com.spendwise.repository;

import com.spendwise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user using email
    Optional<User> findByEmail(String email);

    // Check whether email already exists
    boolean existsByEmail(String email);
}
