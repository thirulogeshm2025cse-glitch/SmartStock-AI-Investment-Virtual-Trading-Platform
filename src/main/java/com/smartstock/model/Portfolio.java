package com.smartstock.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Model representing the aggregated investment portfolio for a user.
 */
public class Portfolio {
    private String portfolioId;
    private String userId;
    private Map<String, Holding> holdings;
    private LocalDateTime lastUpdated;

    public Portfolio() {
        this.holdings = new HashMap<>();
        this.lastUpdated = LocalDateTime.now();
    }

    public Portfolio(String portfolioId, String userId) {
        this.portfolioId = portfolioId;
        this.userId = userId;
        this.holdings = new HashMap<>();
        this.lastUpdated = LocalDateTime.now();
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Holding> getHoldings() {
        return Collections.unmodifiableMap(holdings);
    }

    public void setHoldings(Map<String, Holding> holdings) {
        this.holdings = holdings != null ? new HashMap<>(holdings) : new HashMap<>();
        this.lastUpdated = LocalDateTime.now();
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void addOrUpdateHolding(Holding holding) {
        if (holding != null) {
            this.holdings.put(holding.getStockSymbol().toUpperCase(), holding);
            this.lastUpdated = LocalDateTime.now();
        }
    }

    public Holding getHolding(String stockSymbol) {
        if (stockSymbol == null) return null;
        return this.holdings.get(stockSymbol.toUpperCase());
    }

    public boolean removeHolding(String stockSymbol) {
        if (stockSymbol == null) return false;
        boolean removed = this.holdings.remove(stockSymbol.toUpperCase()) != null;
        if (removed) {
            this.lastUpdated = LocalDateTime.now();
        }
        return removed;
    }

    public double getTotalCostBasis() {
        return holdings.values().stream()
                .mapToDouble(Holding::getTotalCostBasis)
                .sum();
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioId='" + portfolioId + '\'' +
                ", userId='" + userId + '\'' +
                ", totalHoldingsCount=" + holdings.size() +
                ", totalInvested=$" + String.format("%.2f", getTotalCostBasis()) +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
