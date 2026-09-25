package com.smartstock.service;

import com.smartstock.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Service contract for User management, registration, and authentication.
 */
public interface UserService {

    /**
     * Registers a new user with custom identifier and initial virtual balance.
     *
     * @param userId         Unique identifier
     * @param name           User's full name
     * @param email          User's email address
     * @param username       User's chosen username
     * @param password       User's password
     * @param initialBalance Initial virtual trading balance (> 0)
     * @return The registered User entity
     */
    User register(String userId, String name, String email, String username, String password, double initialBalance);

    /**
     * Convenience registration with auto-generated ID and default $10,000 balance.
     */
    User register(String username, String email, String password);

    /**
     * Authenticates credentials and returns the User.
     *
     * @param username User's username
     * @param password User's raw password
     * @return The authenticated User
     */
    User authenticate(String username, String password);

    /**
     * Validates credentials and returns an Optional of User.
     */
    Optional<User> login(String username, String password);

    /**
     * Finds a user by their unique user ID.
     */
    Optional<User> getUserById(String userId);

    /**
     * Finds a user by their username.
     */
    Optional<User> getUserByUsername(String username);

    /**
     * Retrieves the current virtual balance for a user.
     */
    double getBalance(String userId);

    /**
     * Retrieves all registered users.
     */
    List<User> getAllUsers();
}
