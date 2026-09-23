package com.smartstock.service;

import com.smartstock.model.Stock;
import java.util.List;
import java.util.Optional;

/**
 * Service contract for Stock discovery and quote retrieval.
 */
public interface StockService {
    List<Stock> getAllStocks();
    Optional<Stock> getStockBySymbol(String symbol);
    List<Stock> getStocksBySector(String sector);
}
