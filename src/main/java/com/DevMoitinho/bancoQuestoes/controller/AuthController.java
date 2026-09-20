package com.DevMoitinho.bancoQuestoes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DevMoitinho.bancoQuestoes.dto.login.LoginRequestDTO;
import com.DevMoitinho.bancoQuestoes.dto.login.LoginResponseDTO;
import com.DevMoitinho.bancoQuestoes.security.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for authentication and token management.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param request The login request containing username and password.
     * @return A response entity containing the JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        return ResponseEntity.status(HttpStatus.OK)
                            .body(new LoginResponseDTO(jwtService.generateToken(request.getUsername())));
    }
}
