package com.smartstock.service.impl;

import com.smartstock.repository.StockRepository;
import com.smartstock.service.PredictionService;

/**
 * Foundation implementation of PredictionService.
 * Algorithmic/heuristic AI prediction modeling will be implemented in Day 6.
 */
public class PredictionServiceImpl implements PredictionService {
    private final StockRepository stockRepository;

    public PredictionServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public String getPrediction(String symbol) {
        // Foundation placeholder - AI prediction engine scheduled for Day 6
        throw new UnsupportedOperationException("AI prediction model will be fully implemented in Day 6.");
    }
}
