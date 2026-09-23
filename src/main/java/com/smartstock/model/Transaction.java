package com.smartstock.model;

import java.time.LocalDateTime;

/**
 * Model representing a completed virtual stock order/transaction.
 */
public class Transaction {
    private String transactionId;
    private String userId;
    private String stockSymbol;
    private TransactionType type;
    private int quantity;
    private double pricePerShare;
    private double totalAmount;
    private LocalDateTime timestamp;

    public Transaction() {
        this.timestamp = LocalDateTime.now();
    }

    public Transaction(String transactionId, String userId, String stockSymbol,
                       TransactionType type, int quantity, double pricePerShare) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.stockSymbol = stockSymbol;
        this.type = type;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.totalAmount = quantity * pricePerShare;
        this.timestamp = LocalDateTime.now();
    }

    public Transaction(String transactionId, String userId, String stockSymbol,
                       TransactionType type, int quantity, double pricePerShare,
                       double totalAmount, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.stockSymbol = stockSymbol;
        this.type = type;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.totalAmount = totalAmount;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-4s %4d x %-5s @ $%8.2f | Total: $%9.2f | Date: %s",
                transactionId, type, quantity, stockSymbol, pricePerShare, totalAmount, timestamp);
    }
}
