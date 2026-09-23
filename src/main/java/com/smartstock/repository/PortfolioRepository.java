package com.smartstock.repository;

import com.smartstock.model.Portfolio;
import java.util.Optional;

/**
 * Data access contract for User Portfolios.
 */
public interface PortfolioRepository {
    Portfolio save(Portfolio portfolio);
    Optional<Portfolio> findByUserId(String userId);
    Optional<Portfolio> findById(String portfolioId);
    boolean existsByUserId(String userId);
}
