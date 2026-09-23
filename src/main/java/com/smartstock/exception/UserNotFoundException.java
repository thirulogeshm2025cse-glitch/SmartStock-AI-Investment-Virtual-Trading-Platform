package com.smartstock.exception;

/**
 * Exception thrown when a requested user cannot be found in the repository.
 */
public class UserNotFoundException extends SmartStockException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
