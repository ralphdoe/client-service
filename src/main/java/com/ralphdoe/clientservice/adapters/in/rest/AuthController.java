package com.ralphdoe.clientservice.adapters.in.rest;

import com.ralphdoe.clientservice.adapters.in.rest.dto.AuthRequest;
import com.ralphdoe.clientservice.adapters.in.rest.dto.AuthResponse;
import com.ralphdoe.clientservice.config.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        log.info("Attempting authentication for user: {}", request.getUsername());
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtUtil.generateToken(auth.getName());
        log.info("Authentication successful for user: {}. Token generated.", auth.getName());
        return new AuthResponse(token);
    }
}
