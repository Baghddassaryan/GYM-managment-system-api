package org.bagdev.gymmanagmentsystemapi.controller;

import org.bagdev.gymmanagmentsystemapi.model.RefreshToken;
import org.bagdev.gymmanagmentsystemapi.model.User;
import org.bagdev.gymmanagmentsystemapi.model.enums.Role;
import org.bagdev.gymmanagmentsystemapi.repository.UserRepository;
import org.bagdev.gymmanagmentsystemapi.service.impl.JwtService;
import org.bagdev.gymmanagmentsystemapi.service.impl.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserRepository userRepository;
    @Autowired private JwtService jwtService;
    @Autowired private RefreshTokenService refreshTokenService;
    @Autowired private PasswordEncoder passwordEncoder;

    // DTOs (Data Transfer Objects) - Simple inner classes or separate files
    public record RegisterRequest(String email, String password, String firstName, String lastName) {}
    public record LoginRequest(String email, String password) {}
    public record AuthResponse(String accessToken, String refreshToken) {}
    public record RefreshTokenRequest(String refreshToken) {}

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.badRequest().build(); // Email already exists
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setRoles(Set.of(Role.ROLE_USER)); // Default role (Assuming you have Role.USER)

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        return ResponseEntity.ok(new AuthResponse(jwtToken, refreshToken.getToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        // ROTATION: Create new refresh token (old one deleted inside service)
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        return ResponseEntity.ok(new AuthResponse(jwtToken, refreshToken.getToken()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return refreshTokenService.findByToken(request.refreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    // ROTATION: Delete old refresh token used in request, generate brand new one
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getEmail());

                    return ResponseEntity.ok(new AuthResponse(accessToken, newRefreshToken.getToken()));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }
}