package com.example.quiz.service;

import com.example.quiz.dto.LoginRequestDTO;
import com.example.quiz.dto.UsersRequestDTO;
import com.example.quiz.dto.UsersResponseDTO;
import com.example.quiz.entity.Users;
import com.example.quiz.repository.UsersRepository;
import com.example.quiz.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.quiz.enums.Role;

@Service
public class UsersService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UsersResponseDTO register(UsersRequestDTO request) {

        // (optional) prevent duplicate email
        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Email already exists");
                });

        // DTO → Entity
        Users user = new Users();
        user.setName(request.getName());

        user.setEmail(request.getEmail());
        // user.setPassword(request.getPassword()); // encrypt later
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER); // pass enum constant directly

        user = userRepository.save(user);

        // Entity → DTO
        UsersResponseDTO res = new UsersResponseDTO();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setEmail(user.getEmail());

        return res;
    }

    public String login(LoginRequestDTO request) {

        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // pass role to token ← update this line
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }
}
