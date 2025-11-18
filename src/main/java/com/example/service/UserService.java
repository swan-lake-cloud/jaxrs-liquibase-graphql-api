package com.example.service;

import com.example.dto.CreateUserResponse;
import com.example.model.User;
import com.example.user.UserRepository;
import com.example.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CreateUserResponse createUser(String firstName, String lastName, String email, String username, String passwordHash) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPasswordHash(passwordHash);
        User savedUser = addUser(user);

        return new CreateUserResponse(
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getUsername()
        );
    }

    public User addUser(User user) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create user", e);
        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
