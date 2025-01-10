package com.devproject.booking.movie.service;

import com.devproject.booking.movie.dto.request.RegisterRequest;
import com.devproject.booking.movie.entity.Role;
import com.devproject.booking.movie.entity.User;
import com.devproject.booking.movie.exception.CustomDuplicateException;
import com.devproject.booking.movie.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
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

    public void sendResetPasswordEmail(@NotBlank(message = "email cannot be empty") String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setTokenExpirationTime(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        System.out.println("Reached here...");
        String resetLink = "http://localhost:8080/auth/reset-password?token=" + token;
        sendEmail(user.getEmail(), "Click here to reset your password: " + resetLink + ".This is valid for only 15 minutes");

    }

    private void sendEmail(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Reset Password Movie App");
        message.setText(text);
        mailSender.send(message);
    }

    public void restPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (user.getTokenExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setTokenExpirationTime(null);
        userRepository.save(user);
    }
}
