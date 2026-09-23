package com.smartstock.repository.impl;

import com.smartstock.model.Portfolio;
import com.smartstock.repository.PortfolioRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of PortfolioRepository.
 */
public class InMemoryPortfolioRepository implements PortfolioRepository {
    private final Map<String, Portfolio> portfolioStorage = new ConcurrentHashMap<>();
    private final Map<String, String> userToPortfolioMap = new ConcurrentHashMap<>();

    @Override
    public Portfolio save(Portfolio portfolio) {
        if (portfolio == null || portfolio.getUserId() == null) {
            throw new IllegalArgumentException("Portfolio and associated userId cannot be null");
        }
        if (portfolio.getPortfolioId() == null || portfolio.getPortfolioId().isBlank()) {
            portfolio.setPortfolioId(UUID.randomUUID().toString());
        }
        portfolioStorage.put(portfolio.getPortfolioId(), portfolio);
        userToPortfolioMap.put(portfolio.getUserId(), portfolio.getPortfolioId());
        return portfolio;
    }

    @Override
    public Optional<Portfolio> findByUserId(String userId) {
        if (userId == null) return Optional.empty();
        String portfolioId = userToPortfolioMap.get(userId);
        if (portfolioId == null) return Optional.empty();
        return Optional.ofNullable(portfolioStorage.get(portfolioId));
    }

    @Override
    public Optional<Portfolio> findById(String portfolioId) {
        if (portfolioId == null) return Optional.empty();
        return Optional.ofNullable(portfolioStorage.get(portfolioId));
    }

    @Override
    public boolean existsByUserId(String userId) {
        return userId != null && userToPortfolioMap.containsKey(userId);
    }
}
