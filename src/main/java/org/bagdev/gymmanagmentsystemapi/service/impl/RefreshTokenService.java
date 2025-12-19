package org.bagdev.gymmanagmentsystemapi.service.impl;

import org.bagdev.gymmanagmentsystemapi.model.RefreshToken;
import org.bagdev.gymmanagmentsystemapi.model.User;
import org.bagdev.gymmanagmentsystemapi.repository.RefreshTokenRepository;
import org.bagdev.gymmanagmentsystemapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ROTATION LOGIC: Check if user has an existing token and delete it
        Optional<RefreshToken> oldToken = refreshTokenRepository.findByUser(user);
        oldToken.ifPresent(token -> refreshTokenRepository.delete(token));

        // Create new token
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(604800000)); // 7 days
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new sign-in request");
        }
        return token;
    }
}