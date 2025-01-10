package com.devproject.booking.movie.repository;


import com.devproject.booking.movie.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(@NotBlank(message = "email cannot be empty") String email);

    Optional<User> findByResetToken(String token);
}
