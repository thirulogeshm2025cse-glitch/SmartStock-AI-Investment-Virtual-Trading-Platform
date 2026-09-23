package com.smartstock.service.impl;

import com.smartstock.model.Stock;
import com.smartstock.repository.StockRepository;
import com.smartstock.service.StockService;

import java.util.List;
import java.util.Optional;

/**
 * Foundation implementation of StockService reading from StockRepository.
 */
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Optional<Stock> getStockBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol);
    }

    @Override
    public List<Stock> getStocksBySector(String sector) {
        return stockRepository.findBySector(sector);
    }
}
