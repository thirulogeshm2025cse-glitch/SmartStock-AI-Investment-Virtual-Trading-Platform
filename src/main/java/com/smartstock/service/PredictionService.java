package com.smartstock.service;

/**
 * Service contract for AI-based stock price prediction and investment recommendations.
 */
public interface PredictionService {
    String getPrediction(String symbol);
}
