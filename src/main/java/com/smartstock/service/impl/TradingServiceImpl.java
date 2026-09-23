package com.smartstock.service.impl;

import com.smartstock.model.Transaction;
import com.smartstock.repository.PortfolioRepository;
import com.smartstock.repository.StockRepository;
import com.smartstock.repository.TransactionRepository;
import com.smartstock.repository.UserRepository;
import com.smartstock.service.TradingService;

/**
 * Foundation implementation of TradingService.
 * Execution logic and balance validation will be implemented in Day 4.
 */
public class TradingServiceImpl implements TradingService {
    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final PortfolioRepository portfolioRepository;
    private final TransactionRepository transactionRepository;

    public TradingServiceImpl(UserRepository userRepository,
                              StockRepository stockRepository,
                              PortfolioRepository portfolioRepository,
                              TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.stockRepository = stockRepository;
        this.portfolioRepository = portfolioRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction buyStock(String userId, String symbol, int quantity) {
        // Foundation placeholder - full execution engine scheduled for Day 4
        throw new UnsupportedOperationException("Stock purchase execution engine will be fully implemented in Day 4.");
    }

    @Override
    public Transaction sellStock(String userId, String symbol, int quantity) {
        // Foundation placeholder - full execution engine scheduled for Day 4
        throw new UnsupportedOperationException("Stock sale execution engine will be fully implemented in Day 4.");
    }
}
