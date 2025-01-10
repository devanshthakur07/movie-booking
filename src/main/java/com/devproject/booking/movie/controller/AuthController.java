package com.devproject.booking.movie.controller;


import com.devproject.booking.movie.dto.request.ForgotPasswordRequest;
import com.devproject.booking.movie.dto.request.LoginRequest;
import com.devproject.booking.movie.dto.request.RegisterRequest;
import com.devproject.booking.movie.dto.request.ResetPasswordRequest;
import com.devproject.booking.movie.repository.UserRepository;
import com.devproject.booking.movie.service.UserService;
import com.devproject.booking.movie.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.saveUser(registerRequest);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        System.out.println(userDetails.getAuthorities());
        String token = jwtUtil.generateToken(authentication.getName(), userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new RuntimeException("Role not found")));
        System.out.println(jwtUtil.extractRole(token));
        return token;
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        userService.sendResetPasswordEmail(forgotPasswordRequest.email());
        return "A password reset link has been sent to your mail address. Please check your inbox";
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody ResetPasswordRequest resetPasswordRequest) {
        System.out.println();
        userService.restPassword(token, resetPasswordRequest.newPassword());
        return ResponseEntity.ok("Password reset successful");
    }

    @GetMapping("/logout")
    public String logout() {
        return "Logged out successfully";
    }

}
