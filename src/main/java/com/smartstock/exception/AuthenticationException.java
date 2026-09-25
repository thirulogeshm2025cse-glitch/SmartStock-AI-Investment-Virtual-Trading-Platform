package com.smartstock.exception;

/**
 * Exception thrown when user authentication fails due to incorrect credentials.
 */
public class AuthenticationException extends SmartStockException {
    public AuthenticationException(String message) {
        super(message);
    }
}
