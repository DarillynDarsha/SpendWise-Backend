package com.spendwise.controller;

import com.spendwise.entity.User;
import com.spendwise.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // =====================================
    // GET LOGGED-IN USER PROFILE
    // =====================================

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        Map<String, Object> response = new HashMap<>();

        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("phone", user.getPhone());

        return ResponseEntity.ok(response);
    }

    // =====================================
    // UPDATE LOGGED-IN USER PROFILE
    // =====================================

    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(
            Authentication authentication,
            @RequestBody Map<String, String> request) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        String name = request.get("name");
        String phone = request.get("phone");

        if (name == null || name.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Please fill all the fields"));
        }

        user.setName(name.trim());
        user.setPhone(phone.trim());

        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();

        response.put("message", "Profile updated successfully");
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("phone", user.getPhone());

        return ResponseEntity.ok(response);
    }
}
