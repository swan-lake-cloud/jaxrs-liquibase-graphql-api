package com.example.service;

import com.example.model.User;
import com.example.user.UserRepository;
import com.example.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(String identifier, String password) {
        User user = userRepository.findByUsernameOrEmail(identifier);
        if (user == null) {
            throw new RuntimeException("Invalid credentials");
        }
        if (!BCrypt.checkpw(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        return JwtUtil.generateToken(user.getUsername());
    }
}
