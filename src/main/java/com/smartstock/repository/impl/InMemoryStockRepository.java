package com.smartstock.repository.impl;

import com.smartstock.model.Stock;
import com.smartstock.repository.StockRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of StockRepository pre-seeded with market assets.
 */
public class InMemoryStockRepository implements StockRepository {
    private final Map<String, Stock> stockStorage = new ConcurrentHashMap<>();

    public InMemoryStockRepository() {
        seedInitialStocks();
    }

    private void seedInitialStocks() {
        save(new Stock("AAPL", "Apple Inc.", 189.50, "Technology", 1.25, 191.00, 187.80));
        save(new Stock("MSFT", "Microsoft Corp.", 425.20, "Technology", 0.85, 428.00, 422.10));
        save(new Stock("GOOGL", "Alphabet Inc.", 178.60, "Technology", -0.45, 180.20, 177.10));
        save(new Stock("AMZN", "Amazon.com Inc.", 185.30, "Consumer Cyclical", 2.10, 186.50, 182.40));
        save(new Stock("TSLA", "Tesla Inc.", 248.90, "Automotive", -1.80, 254.00, 245.50));
        save(new Stock("NVDA", "NVIDIA Corp.", 124.40, "Semiconductors", 3.40, 126.00, 121.20));
    }

    @Override
    public Stock save(Stock stock) {
        if (stock == null || stock.getSymbol() == null) {
            throw new IllegalArgumentException("Stock and symbol cannot be null");
        }
        stockStorage.put(stock.getSymbol().toUpperCase(), stock);
        return stock;
    }

    @Override
    public Optional<Stock> findBySymbol(String symbol) {
        if (symbol == null) return Optional.empty();
        return Optional.ofNullable(stockStorage.get(symbol.toUpperCase().trim()));
    }

    @Override
    public List<Stock> findAll() {
        return new ArrayList<>(stockStorage.values());
    }

    @Override
    public List<Stock> findBySector(String sector) {
        if (sector == null) return Collections.emptyList();
        return stockStorage.values().stream()
                .filter(s -> s.getSector().equalsIgnoreCase(sector.trim()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsBySymbol(String symbol) {
        return findBySymbol(symbol).isPresent();
    }
}
