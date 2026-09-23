package com.smartstock.model;

/**
 * Model representing a user's holding of a specific stock.
 */
public class Holding {
    private String holdingId;
    private String stockSymbol;
    private int quantity;
    private double averageBuyPrice;

    public Holding() {
    }

    public Holding(String holdingId, String stockSymbol, int quantity, double averageBuyPrice) {
        this.holdingId = holdingId;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.averageBuyPrice = averageBuyPrice;
    }

    public String getHoldingId() {
        return holdingId;
    }

    public void setHoldingId(String holdingId) {
        this.holdingId = holdingId;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAverageBuyPrice() {
        return averageBuyPrice;
    }

    public void setAverageBuyPrice(double averageBuyPrice) {
        this.averageBuyPrice = averageBuyPrice;
    }

    /**
     * Calculates the total amount invested in this holding.
     */
    public double getTotalCostBasis() {
        return quantity * averageBuyPrice;
    }

    @Override
    public String toString() {
        return String.format("Holding [%s] - Shares: %d | Avg Price: $%.2f | Total Cost: $%.2f",
                stockSymbol, quantity, averageBuyPrice, getTotalCostBasis());
    }
}
