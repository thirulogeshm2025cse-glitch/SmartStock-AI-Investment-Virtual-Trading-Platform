package com.smartstock.service.impl;

import com.smartstock.exception.AuthenticationException;
import com.smartstock.exception.InvalidUserDataException;
import com.smartstock.exception.UserAlreadyExistsException;
import com.smartstock.exception.UserNotFoundException;
import com.smartstock.model.Portfolio;
import com.smartstock.model.User;
import com.smartstock.repository.PortfolioRepository;
import com.smartstock.repository.UserRepository;
import com.smartstock.service.UserService;
import com.smartstock.utility.InputValidator;
import com.smartstock.utility.PasswordUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Production-ready implementation of UserService providing registration,
 * field validation, duplicate detection, SHA-256 password hashing,
 * and credential authentication.
 */
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this(userRepository, null);
    }

    public UserServiceImpl(UserRepository userRepository, PortfolioRepository portfolioRepository) {
        if (userRepository == null) {
            throw new IllegalArgumentException("UserRepository cannot be null");
        }
        this.userRepository = userRepository;
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public User register(String userId, String name, String email, String username, String password, double initialBalance) {
        // 1. Validate empty/blank fields
        if (userId == null || userId.isBlank()) {
            throw new InvalidUserDataException("User ID cannot be empty or blank.");
        }
        if (name == null || name.isBlank()) {
            throw new InvalidUserDataException("Name cannot be empty or blank.");
        }
        if (email == null || email.isBlank()) {
            throw new InvalidUserDataException("Email cannot be empty or blank.");
        }
        if (username == null || username.isBlank()) {
            throw new InvalidUserDataException("Username cannot be empty or blank.");
        }
        if (password == null || password.isBlank()) {
            throw new InvalidUserDataException("Password cannot be empty or blank.");
        }

        // 2. Validate email format
        if (!InputValidator.isValidEmail(email)) {
            throw new InvalidUserDataException("Invalid email format: '" + email + "'. Expected format: user@example.com");
        }

        // 3. Validate username minimum length
        if (!InputValidator.isValidUsername(username)) {
            throw new InvalidUserDataException("Username must be at least 3 characters long.");
        }

        // 4. Validate password minimum length
        if (!InputValidator.isValidPassword(password)) {
            throw new InvalidUserDataException("Password must be at least 4 characters long.");
        }

        // 5. Validate initial balance
        if (!InputValidator.isValidInitialBalance(initialBalance)) {
            throw new InvalidUserDataException("Initial virtual balance must be greater than zero. Provided: " + initialBalance);
        }

        // 6. Check for duplicate User ID
        if (userRepository.existsById(userId.trim())) {
            throw new UserAlreadyExistsException("User ID '" + userId.trim() + "' is already in use. Please choose a different User ID.");
        }

        // 7. Check for duplicate username (case-insensitive)
        if (userRepository.existsByUsername(username.trim())) {
            throw new UserAlreadyExistsException("Username '" + username.trim() + "' is already taken. Please choose another username.");
        }

        // 8. Check for duplicate email (case-insensitive)
        if (userRepository.existsByEmail(email.trim())) {
            throw new UserAlreadyExistsException("Email '" + email.trim() + "' is already registered. Please use another email address.");
        }

        // 9. Hash password using SHA-256
        String hashedPassword = PasswordUtils.hashPassword(password);

        // 10. Construct and persist User entity
        User newUser = new User(
                userId.trim(),
                name.trim(),
                email.trim().toLowerCase(),
                username.trim(),
                hashedPassword,
                initialBalance
        );
        User savedUser = userRepository.save(newUser);

        // 11. Provision initial portfolio if repository is configured
        if (portfolioRepository != null && !portfolioRepository.existsByUserId(savedUser.getUserId())) {
            portfolioRepository.save(new Portfolio(UUID.randomUUID().toString(), savedUser.getUserId()));
        }

        return savedUser;
    }

    @Override
    public User register(String username, String email, String password) {
        String generatedId = "USR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return register(generatedId, username, email, username, password, 10000.0);
    }

    @Override
    public User authenticate(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new InvalidUserDataException("Username and password cannot be empty.");
        }

        User user = userRepository.findByUsername(username.trim())
                .orElseThrow(() -> new UserNotFoundException("User '" + username.trim() + "' not found. Please register first."));

        if (!PasswordUtils.verifyPassword(password, user.getPasswordHash())) {
            throw new AuthenticationException("Invalid password for user '" + username.trim() + "'. Please try again.");
        }

        return user;
    }

    @Override
    public Optional<User> login(String username, String password) {
        try {
            return Optional.of(authenticate(username, password));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserById(String userId) {
        if (userId == null || userId.isBlank()) {
            return Optional.empty();
        }
        return userRepository.findById(userId.trim());
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        if (username == null || username.isBlank()) {
            return Optional.empty();
        }
        return userRepository.findByUsername(username.trim());
    }

    @Override
    public double getBalance(String userId) {
        return getUserById(userId)
                .map(User::getVirtualBalance)
                .orElse(0.0);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.findAll());
    }
}
