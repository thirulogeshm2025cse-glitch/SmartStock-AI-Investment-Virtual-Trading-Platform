package com.smartstock.exception;

/**
 * Exception thrown when a user attempts to sell more shares than they currently own.
 */
public class InsufficientSharesException extends SmartStockException {
    public InsufficientSharesException(String message) {
        super(message);
    }
}
