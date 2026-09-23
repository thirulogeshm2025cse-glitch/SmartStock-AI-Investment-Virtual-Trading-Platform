package com.smartstock.exception;

/**
 * Base custom unchecked exception for the SmartStock platform.
 */
public class SmartStockException extends RuntimeException {
    public SmartStockException(String message) {
        super(message);
    }

    public SmartStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
