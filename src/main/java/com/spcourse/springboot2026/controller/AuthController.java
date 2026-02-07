package com.spcourse.springboot2026.controller;

import com.spcourse.springboot2026.dto.LoginRequest;
import com.spcourse.springboot2026.entity.SchoolUser;
import com.spcourse.springboot2026.repository.UserRepository;
import com.spcourse.springboot2026.service.impl.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository; // DI
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody SchoolUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        SchoolUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(user);
    }
}
