package com.example.graphql.resolver;

import com.example.model.User;
import com.example.user.UserRepository;
import com.example.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;

public class UserMutationResolver {
    private final UserRepository userRepository = new UserRepository();

    public UserMutationResolver() {

    }

    public String login(String identifier, String password) {
        User user = this.userRepository.findByUsernameOrEmail(identifier);
        if (user == null || !BCrypt.checkpw(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        return JwtUtil.generateToken(user.getUsername());
    }
}
