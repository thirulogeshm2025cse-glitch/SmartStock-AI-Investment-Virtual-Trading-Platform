package com.smartstock.repository;

import com.smartstock.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Data access contract for User entities.
 */
public interface UserRepository {
    User save(User user);
    Optional<User> findById(String userId);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void deleteById(String userId);
}
