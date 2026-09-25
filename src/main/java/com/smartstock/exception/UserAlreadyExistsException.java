package com.smartstock.exception;

/**
 * Exception thrown when attempting to register a user with an identifier,
 * username, or email that already exists in the system.
 */
public class UserAlreadyExistsException extends SmartStockException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
