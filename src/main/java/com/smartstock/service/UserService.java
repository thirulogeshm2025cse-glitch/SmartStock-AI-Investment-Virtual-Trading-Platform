package com.smartstock.service;

import com.smartstock.model.User;
import java.util.Optional;

/**
 * Service contract for User management and authentication.
 */
public interface UserService {
    User register(String username, String email, String password);
    Optional<User> login(String username, String password);
    Optional<User> getUserById(String userId);
    double getBalance(String userId);
}
