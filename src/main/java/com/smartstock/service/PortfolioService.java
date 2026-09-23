package com.smartstock.service;

import com.smartstock.model.Portfolio;

import java.util.Optional;

/**
 * Service contract for Portfolio tracking, valuation, and holdings.
 */
public interface PortfolioService {
    Optional<Portfolio> getPortfolioByUserId(String userId);
    double calculateTotalPortfolioValue(String userId);
}
