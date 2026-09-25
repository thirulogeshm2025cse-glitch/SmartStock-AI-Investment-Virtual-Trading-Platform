package com.smartstock.exception;

/**
 * Exception thrown when user-provided data fails validation (empty fields,
 * invalid format, or non-positive virtual balance).
 */
public class InvalidUserDataException extends SmartStockException {
    public InvalidUserDataException(String message) {
        super(message);
    }
}
