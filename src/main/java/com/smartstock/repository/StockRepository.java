package com.smartstock.repository;

import com.smartstock.model.Stock;
import java.util.List;
import java.util.Optional;

/**
 * Data access contract for Stock market entities.
 */
public interface StockRepository {
    Stock save(Stock stock);
    Optional<Stock> findBySymbol(String symbol);
    List<Stock> findAll();
    List<Stock> findBySector(String sector);
    boolean existsBySymbol(String symbol);
}
