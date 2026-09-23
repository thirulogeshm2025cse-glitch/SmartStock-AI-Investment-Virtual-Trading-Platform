package com.smartstock.service.impl;

import com.smartstock.model.Portfolio;
import com.smartstock.repository.PortfolioRepository;
import com.smartstock.repository.StockRepository;
import com.smartstock.service.PortfolioService;

import java.util.Optional;

/**
 * Foundation implementation of PortfolioService.
 * Detailed valuation and unrealized P&L calculations will be implemented in Day 5.
 */
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;

    public PortfolioServiceImpl(PortfolioRepository portfolioRepository, StockRepository stockRepository) {
        this.portfolioRepository = portfolioRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public Optional<Portfolio> getPortfolioByUserId(String userId) {
        return portfolioRepository.findByUserId(userId);
    }

    @Override
    public double calculateTotalPortfolioValue(String userId) {
        // Foundation placeholder - full valuation engine scheduled for Day 5
        throw new UnsupportedOperationException("Portfolio valuation calculation will be fully implemented in Day 5.");
    }
}
