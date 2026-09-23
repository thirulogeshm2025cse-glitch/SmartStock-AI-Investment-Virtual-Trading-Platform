package com.smartstock.controller;

import com.smartstock.model.Stock;
import com.smartstock.service.PortfolioService;
import com.smartstock.service.PredictionService;
import com.smartstock.service.StockService;
import com.smartstock.service.TradingService;
import com.smartstock.service.UserService;
import com.smartstock.utility.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

/**
 * Controller managing console menu interactions, displaying options,
 * and dispatching user selections to the appropriate service layers.
 */
public class ConsoleMenuController {
    private final UserService userService;
    private final StockService stockService;
    private final TradingService tradingService;
    private final PortfolioService portfolioService;
    private final PredictionService predictionService;
    private final Scanner scanner;

    public ConsoleMenuController(UserService userService,
                                 StockService stockService,
                                 TradingService tradingService,
                                 PortfolioService portfolioService,
                                 PredictionService predictionService,
                                 Scanner scanner) {
        this.userService = userService;
        this.stockService = stockService;
        this.tradingService = tradingService;
        this.portfolioService = portfolioService;
        this.predictionService = predictionService;
        this.scanner = scanner;
    }

    /**
     * Renders the primary console menu.
     */
    public void displayMenu() {
        System.out.println("\n========== SMARTSTOCK MAIN MENU ==========");
        System.out.println(" 1. Register");
        System.out.println(" 2. Login");
        System.out.println(" 3. View Stocks");
        System.out.println(" 4. Buy Stock");
        System.out.println(" 5. Sell Stock");
        System.out.println(" 6. View Portfolio");
        System.out.println(" 7. View Transactions");
        System.out.println(" 8. AI Prediction");
        System.out.println(" 9. Exit");
        System.out.println("==========================================");
        System.out.print("Enter your choice (1-9): ");
    }

    /**
     * Dispatches user selection to the appropriate handler.
     * @return true to continue loop, false to exit.
     */
    public boolean processChoice(String input) {
        if (input == null || input.isBlank()) {
            ConsoleUtils.printWarning("Please enter a valid option between 1 and 9.");
            return true;
        }

        switch (input.trim()) {
            case "1":
                handleRegister();
                return true;
            case "2":
                handleLogin();
                return true;
            case "3":
                handleViewStocks();
                return true;
            case "4":
                handleBuyStock();
                return true;
            case "5":
                handleSellStock();
                return true;
            case "6":
                handleViewPortfolio();
                return true;
            case "7":
                handleViewTransactions();
                return true;
            case "8":
                handleAIPrediction();
                return true;
            case "9":
                handleExit();
                return false;
            default:
                ConsoleUtils.printWarning("Invalid option '" + input.trim() + "'. Please choose between 1 and 9.");
                return true;
        }
    }

    private void handleRegister() {
        ConsoleUtils.printSectionHeader("1. User Registration");
        ConsoleUtils.printInfo("[Day 1 Foundation] User Registration architecture is initialized.");
        ConsoleUtils.printInfo("Scheduled for full interactive implementation in Day 2 (User Management & Auth).");
    }

    private void handleLogin() {
        ConsoleUtils.printSectionHeader("2. User Login");
        ConsoleUtils.printInfo("[Day 1 Foundation] User Login architecture is initialized.");
        ConsoleUtils.printInfo("Scheduled for full interactive implementation in Day 2 (User Management & Auth).");
    }

    private void handleViewStocks() {
        ConsoleUtils.printSectionHeader("3. View Market Stocks");
        List<Stock> stocks = stockService.getAllStocks();
        if (stocks.isEmpty()) {
            ConsoleUtils.printWarning("No stock data currently loaded in repository.");
            return;
        }

        System.out.println(String.format("%-8s | %-22s | %-12s | %-10s | %-16s",
                "SYMBOL", "COMPANY NAME", "PRICE", "24H CHG", "SECTOR"));
        ConsoleUtils.printDivider();
        for (Stock stock : stocks) {
            String chg = String.format("%s%.2f%%", stock.getChangePercent() >= 0 ? "+" : "", stock.getChangePercent());
            System.out.println(String.format("%-8s | %-22s | %-12s | %-10s | %-16s",
                    stock.getSymbol(),
                    stock.getCompanyName(),
                    ConsoleUtils.formatCurrency(stock.getCurrentPrice()),
                    chg,
                    stock.getSector()));
        }
        ConsoleUtils.printDivider();
        ConsoleUtils.printInfo("Live ticker ingestion and updates scheduled for Day 3.");
    }

    private void handleBuyStock() {
        ConsoleUtils.printSectionHeader("4. Buy Stock (Virtual Trading)");
        ConsoleUtils.printInfo("[Day 1 Foundation] Order routing & transaction models initialized.");
        ConsoleUtils.printInfo("Scheduled for full execution in Day 4 (Virtual Trading Engine).");
    }

    private void handleSellStock() {
        ConsoleUtils.printSectionHeader("5. Sell Stock (Virtual Trading)");
        ConsoleUtils.printInfo("[Day 1 Foundation] Holding verification & sell order models initialized.");
        ConsoleUtils.printInfo("Scheduled for full execution in Day 4 (Virtual Trading Engine).");
    }

    private void handleViewPortfolio() {
        ConsoleUtils.printSectionHeader("6. View Portfolio");
        ConsoleUtils.printInfo("[Day 1 Foundation] Portfolio & Holding structures initialized.");
        ConsoleUtils.printInfo("Scheduled for full tracking & P&L analytics in Day 5 (Portfolio & History).");
    }

    private void handleViewTransactions() {
        ConsoleUtils.printSectionHeader("7. View Transactions");
        ConsoleUtils.printInfo("[Day 1 Foundation] Audit trail & Transaction repository initialized.");
        ConsoleUtils.printInfo("Scheduled for full transaction history display in Day 5 (Portfolio & History).");
    }

    private void handleAIPrediction() {
        ConsoleUtils.printSectionHeader("8. AI Stock Prediction");
        ConsoleUtils.printInfo("[Day 1 Foundation] AI Prediction Service interface initialized.");
        ConsoleUtils.printInfo("Scheduled for algorithmic trend forecasting in Day 6 (AI Stock Prediction).");
    }

    private void handleExit() {
        ConsoleUtils.printSectionHeader("9. Exit");
        ConsoleUtils.printSuccess("Thank you for using SmartStock Platform. Exiting safely...");
    }
}
