package com.devproject.booking.movie.service;

import com.devproject.booking.movie.dto.request.RegisterRequest;
import com.devproject.booking.movie.entity.Role;
import com.devproject.booking.movie.entity.User;
import com.devproject.booking.movie.exception.CustomDuplicateException;
import com.devproject.booking.movie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(RegisterRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email());
        user.setRole(Role.valueOf(userRequest.role()));
        user.setPassword(passwordEncoder.encode(userRequest.password()));

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("username")) {
                throw new CustomDuplicateException("Username already exists");
            } else if (e.getMessage().contains("email")) {
                throw new CustomDuplicateException("Email already exists");
            }
            throw new CustomDuplicateException("Duplicate entry found");
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
