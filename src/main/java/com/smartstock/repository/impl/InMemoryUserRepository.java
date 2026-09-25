package com.smartstock.repository.impl;

import com.smartstock.model.User;
import com.smartstock.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of UserRepository for local state management.
 */
public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> userStorage = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getUserId() == null || user.getUserId().isBlank()) {
            user.setUserId(UUID.randomUUID().toString());
        } else {
            user.setUserId(user.getUserId().trim());
        }
        userStorage.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        if (userId == null) return Optional.empty();
        return Optional.ofNullable(userStorage.get(userId.trim()));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (username == null) return Optional.empty();
        return userStorage.values().stream()
                .filter(u -> u.getUsername() != null && u.getUsername().equalsIgnoreCase(username.trim()))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null) return Optional.empty();
        return userStorage.values().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email.trim()))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userStorage.values());
    }

    @Override
    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    @Override
    public boolean existsById(String userId) {
        if (userId == null) return false;
        return userStorage.containsKey(userId.trim());
    }

    @Override
    public void deleteById(String userId) {
        if (userId != null) {
            userStorage.remove(userId.trim());
        }
    }
}
