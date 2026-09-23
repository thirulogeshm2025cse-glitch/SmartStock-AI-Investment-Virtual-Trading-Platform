package com.smartstock.service.impl;

import com.smartstock.model.User;
import com.smartstock.repository.UserRepository;
import com.smartstock.service.UserService;

import java.util.Optional;

/**
 * Foundation implementation of UserService.
 * Detailed authentication and validation will be implemented in Day 2.
 */
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(String username, String email, String password) {
        // Foundation placeholder - full implementation scheduled for Day 2
        throw new UnsupportedOperationException("User registration flow will be fully implemented in Day 2.");
    }

    @Override
    public Optional<User> login(String username, String password) {
        // Foundation placeholder - full implementation scheduled for Day 2
        throw new UnsupportedOperationException("User login authentication will be fully implemented in Day 2.");
    }

    @Override
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public double getBalance(String userId) {
        return userRepository.findById(userId)
                .map(User::getVirtualBalance)
                .orElse(0.0);
    }
}
