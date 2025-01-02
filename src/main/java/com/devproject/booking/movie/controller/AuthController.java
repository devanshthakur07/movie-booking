package com.devproject.booking.movie.controller;


import com.devproject.booking.movie.dto.LoginRequest;
import com.devproject.booking.movie.dto.RegisterRequest;
import com.devproject.booking.movie.entity.User;
import com.devproject.booking.movie.repository.UserRepository;
import com.devproject.booking.movie.service.UserService;
import com.devproject.booking.movie.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody RegisterRequest registerRequest) {
        userService.saveUser(registerRequest);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.password());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );

        User user = userRepository.findByUsername(loginRequest.username()).orElseThrow(() -> new RuntimeException("User not found"));;
        return jwtUtil.generateToken(authentication.getName(), user.getRole().name());
    }

    @GetMapping("/logout")
    public String logout() {
        return "Logged out successfully";
    }

}
