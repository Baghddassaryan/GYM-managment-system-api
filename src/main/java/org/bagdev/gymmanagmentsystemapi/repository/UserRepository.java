package org.bagdev.gymmanagmentsystemapi.repository;

import org.bagdev.gymmanagmentsystemapi.model.RefreshToken;
import org.bagdev.gymmanagmentsystemapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}