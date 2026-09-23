package com.smartstock.service;

import com.smartstock.model.Transaction;

/**
 * Service contract for buying and selling virtual stocks.
 */
public interface TradingService {
    Transaction buyStock(String userId, String symbol, int quantity);
    Transaction sellStock(String userId, String symbol, int quantity);
}
