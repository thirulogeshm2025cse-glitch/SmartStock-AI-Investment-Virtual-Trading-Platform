package com.smartstock.model;

import java.time.LocalDateTime;

/**
 * Model representing a platform user with virtual trading account details.
 */
public class User {
    private String userId;
    private String username;
    private String passwordHash;
    private String email;
    private double virtualBalance;
    private LocalDateTime createdAt;

    public User() {
        this.createdAt = LocalDateTime.now();
        this.virtualBalance = 10000.0; // Default virtual starting balance
    }

    public User(String userId, String username, String passwordHash, String email, double virtualBalance) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.virtualBalance = virtualBalance;
        this.createdAt = LocalDateTime.now();
    }

    public User(String userId, String username, String passwordHash, String email, double virtualBalance, LocalDateTime createdAt) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.virtualBalance = virtualBalance;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getVirtualBalance() {
        return virtualBalance;
    }

    public void setVirtualBalance(double virtualBalance) {
        this.virtualBalance = virtualBalance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", virtualBalance=$" + String.format("%.2f", virtualBalance) +
                ", createdAt=" + createdAt +
                '}';
    }
}
