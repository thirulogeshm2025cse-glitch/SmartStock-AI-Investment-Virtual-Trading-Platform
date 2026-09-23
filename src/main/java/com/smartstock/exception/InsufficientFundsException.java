package com.smartstock.exception;

/**
 * Exception thrown when a user attempts a buy transaction without adequate virtual balance.
 */
public class InsufficientFundsException extends SmartStockException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
