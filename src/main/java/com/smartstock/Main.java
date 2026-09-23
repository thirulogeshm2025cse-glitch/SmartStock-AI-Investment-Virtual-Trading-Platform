package com.smartstock;

import com.smartstock.controller.ConsoleMenuController;
import com.smartstock.repository.PortfolioRepository;
import com.smartstock.repository.StockRepository;
import com.smartstock.repository.TransactionRepository;
import com.smartstock.repository.UserRepository;
import com.smartstock.repository.impl.InMemoryPortfolioRepository;
import com.smartstock.repository.impl.InMemoryStockRepository;
import com.smartstock.repository.impl.InMemoryTransactionRepository;
import com.smartstock.repository.impl.InMemoryUserRepository;
import com.smartstock.service.PortfolioService;
import com.smartstock.service.PredictionService;
import com.smartstock.service.StockService;
import com.smartstock.service.TradingService;
import com.smartstock.service.UserService;
import com.smartstock.service.impl.PortfolioServiceImpl;
import com.smartstock.service.impl.PredictionServiceImpl;
import com.smartstock.service.impl.StockServiceImpl;
import com.smartstock.service.impl.TradingServiceImpl;
import com.smartstock.service.impl.UserServiceImpl;
import com.smartstock.utility.ConsoleUtils;

import java.util.Scanner;

/**
 * Main application entry point for SmartStock - AI Investment & Virtual Trading Platform.
 * Bootstraps the layered architecture and launches the interactive console menu.
 */
public class Main {

    public static void main(String[] args) {
        ConsoleUtils.printBanner();

        // 1. Initialize Repositories (Data Layer)
        UserRepository userRepository = new InMemoryUserRepository();
        StockRepository stockRepository = new InMemoryStockRepository();
        PortfolioRepository portfolioRepository = new InMemoryPortfolioRepository();
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();

        // 2. Initialize Services (Business Layer)
        UserService userService = new UserServiceImpl(userRepository);
        StockService stockService = new StockServiceImpl(stockRepository);
        TradingService tradingService = new TradingServiceImpl(
                userRepository, stockRepository, portfolioRepository, transactionRepository
        );
        PortfolioService portfolioService = new PortfolioServiceImpl(portfolioRepository, stockRepository);
        PredictionService predictionService = new PredictionServiceImpl(stockRepository);

        // 3. Initialize Controller (Presentation Layer)
        Scanner scanner = new Scanner(System.in);
        ConsoleMenuController controller = new ConsoleMenuController(
                userService, stockService, tradingService, portfolioService, predictionService, scanner
        );

        ConsoleUtils.printInfo("Platform initialized with layered architecture.");
        ConsoleUtils.printInfo("Day 1 Foundation active. Ready for navigation.\n");

        // 4. Interactive Console Loop
        boolean running = true;
        try {
            while (running) {
                controller.displayMenu();
                if (!scanner.hasNextLine()) {
                    break;
                }
                String choice = scanner.nextLine();
                running = controller.processChoice(choice);
            }
        } finally {
            scanner.close();
        }
    }
}
