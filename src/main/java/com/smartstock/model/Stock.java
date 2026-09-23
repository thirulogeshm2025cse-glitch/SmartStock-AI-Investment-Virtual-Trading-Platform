package com.smartstock.model;

/**
 * Model representing a tradable stock asset on the market.
 */
public class Stock {
    private String symbol;
    private String companyName;
    private double currentPrice;
    private String sector;
    private double changePercent;
    private double dayHigh;
    private double dayLow;

    public Stock() {
    }

    public Stock(String symbol, String companyName, double currentPrice, String sector) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.sector = sector;
        this.changePercent = 0.0;
        this.dayHigh = currentPrice;
        this.dayLow = currentPrice;
    }

    public Stock(String symbol, String companyName, double currentPrice, String sector, double changePercent, double dayHigh, double dayLow) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.sector = sector;
        this.changePercent = changePercent;
        this.dayHigh = dayHigh;
        this.dayLow = dayLow;
    }

    // Getters and Setters
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

    public double getDayHigh() {
        return dayHigh;
    }

    public void setDayHigh(double dayHigh) {
        this.dayHigh = dayHigh;
    }

    public double getDayLow() {
        return dayLow;
    }

    public void setDayLow(double dayLow) {
        this.dayLow = dayLow;
    }

    @Override
    public String toString() {
        String sign = changePercent >= 0 ? "+" : "";
        return String.format("[%s] %-20s | Price: $%8.2f | Change: %s%.2f%% | Sector: %s",
                symbol, companyName, currentPrice, sign, changePercent, sector);
    }
}
