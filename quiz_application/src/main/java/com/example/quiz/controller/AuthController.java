package com.example.quiz.controller;

import com.example.quiz.dto.LoginRequestDTO;
import com.example.quiz.dto.UsersRequestDTO;
import com.example.quiz.dto.UsersResponseDTO;
import com.example.quiz.service.UsersService;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    
    @Autowired
    private UsersService userService;

    @PostMapping("/register")
    public ResponseEntity<UsersResponseDTO> register(
            @Valid @RequestBody UsersRequestDTO request) {

        UsersResponseDTO response = userService.register(request);
        return ResponseEntity.ok(response);
    }

/*      @PostMapping("/login")
    public ResponseEntity<UsersResponseDTO> login(@RequestBody LoginRequestDTO request) {
    return ResponseEntity.ok(userService.login(request));
    
} */

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
    String token = userService.login(request);
    return ResponseEntity.ok(Map.of("token", token));
}
    
}