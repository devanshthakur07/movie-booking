package com.devproject.booking.movie.controller;


import com.devproject.booking.movie.dto.request.LoginRequest;
import com.devproject.booking.movie.dto.request.RegisterRequest;
import com.devproject.booking.movie.repository.UserRepository;
import com.devproject.booking.movie.service.UserService;
import com.devproject.booking.movie.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println(loginRequest.password());
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

    @GetMapping("/logout")
    public String logout() {
        return "Logged out successfully";
    }

}
