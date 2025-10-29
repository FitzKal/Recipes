package com.example.demo.service;

import com.example.demo.DTOs.LoginRequestDto;
import com.example.demo.DTOs.LoginResponseDto;
import com.example.demo.DTOs.RegisterRequestDto;
import com.example.demo.DTOs.RegisterResponseDto;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepo;
import com.example.demo.security.PasswordEncrypter;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncrypter passwordEncrypter;
    private final JWTService jwtService;

    // REGISTER -> username + role + (kesobb) token
    public RegisterResponseDto register(RegisterRequestDto req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already taken!");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncrypter.passwordEncoder().encode(req.getPassword()));
        user.setRole(Role.USER); // alapértelmezett szerep
        userRepo.save(user);

        // TODO: ha kész a JWT service, itt kell generalni tokent
        String token = jwtService.generateToken(req.getUsername());

        return RegisterResponseDto.builder()
                .username(user.getUsername())
                .role(user.getRole().name())
                .token(token)
                .build();
    }

    // LOGIN -> username + role + (később) token
    public LoginResponseDto login(LoginRequestDto req) {
        User user = userRepo.findByUsername(req.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found!");
        }
        if (!passwordEncrypter.passwordEncoder().matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        // TODO: ha kész a JWT service, itt kell generalni tokent
        String token = jwtService.generateToken(user.getUsername());

        return LoginResponseDto.builder()
                .username(user.getUsername())
                .role(user.getRole().name())
                .token(token)
                .build();
    }
}
