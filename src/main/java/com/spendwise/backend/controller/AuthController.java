package com.spendwise.controller;

import com.spendwise.entity.User;
import com.spendwise.repository.UserRepository;
import com.spendwise.service.JwtService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // Constructor injection
    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // =====================================
    // USER REGISTRATION / SIGNUP
    // =====================================

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @Valid @RequestBody SignupRequest request) {

        // Check existing email
        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email already registered"));
        }

        // Create new user
        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPhone(request.phone());

        // Encrypt password using BCrypt
        user.setPassword(
                passwordEncoder.encode(request.password())
        );

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "Registration successful"));
    }

    // =====================================
// USER LOGIN - TEMPORARY DIAGNOSTIC
// =====================================

@PostMapping("/login")
public ResponseEntity<?> login(
        @RequestBody LoginRequest request) {

    try {

        // Find user using email
        User user = userRepository.findByEmail(request.email().trim())
                .orElse(null);

        // Check password against stored BCrypt hash
        boolean passwordMatches =
                user != null &&
                passwordEncoder.matches(
                        request.password(),
                        user.getPassword()
                );

        // Print diagnostic results (never print password or hash)
        System.out.println("========== LOGIN DIAGNOSTIC ==========");
        System.out.println("USER FOUND: " + (user != null));
        System.out.println("PASSWORD MATCH: " + passwordMatches);
        System.out.println("======================================");

        // Authenticate credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email().trim(),
                        request.password()
                )
        );

        // Generate JWT token
        String token = jwtService.generateToken(
                request.email().trim()
        );

        return ResponseEntity.ok(
                Map.of(
                        "message", "Login successful",
                        "token", token,
                        "email", request.email().trim()
                )
        );

    } catch (Exception e) {

        // Temporary diagnostic: print error type only
        System.out.println("LOGIN ERROR: " + e.getClass().getSimpleName());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "message", "Invalid email or password"
                ));
    }
}

    // =====================================
    // SIGNUP REQUEST
    // =====================================

    public record SignupRequest(

            @NotBlank(message = "Name is required")
            String name,

            @NotBlank(message = "Email is required")
            @Email(message = "Enter a valid email")
            String email,

            @NotBlank(message = "Password is required")
            @Size(min = 8, message = "Password must contain at least 8 characters")
            String password,

            String phone
    ) {}

    // =====================================
    // LOGIN REQUEST
    // =====================================

    public record LoginRequest(

            @NotBlank(message = "Email is required")
            @Email(message = "Enter a valid email")
            String email,

            @NotBlank(message = "Password is required")
            String password

    ) {}
}