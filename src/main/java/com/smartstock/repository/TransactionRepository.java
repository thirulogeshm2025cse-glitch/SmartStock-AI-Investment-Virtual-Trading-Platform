package com.smartstock.repository;

import com.smartstock.model.Transaction;
import java.util.List;
import java.util.Optional;

/**
 * Data access contract for Order/Trade Transactions.
 */
public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(String transactionId);
    List<Transaction> findByUserId(String userId);
    List<Transaction> findAll();
}
