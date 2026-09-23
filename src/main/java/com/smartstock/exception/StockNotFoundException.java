package com.smartstock.exception;

/**
 * Exception thrown when a requested stock ticker/symbol is not found.
 */
public class StockNotFoundException extends SmartStockException {
    public StockNotFoundException(String message) {
        super(message);
    }
}
