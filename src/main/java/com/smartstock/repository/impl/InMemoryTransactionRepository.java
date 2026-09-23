package com.smartstock.repository.impl;

import com.smartstock.model.Transaction;
import com.smartstock.repository.TransactionRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of TransactionRepository.
 */
public class InMemoryTransactionRepository implements TransactionRepository {
    private final Map<String, Transaction> transactionStorage = new ConcurrentHashMap<>();

    @Override
    public Transaction save(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if (transaction.getTransactionId() == null || transaction.getTransactionId().isBlank()) {
            transaction.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        transactionStorage.put(transaction.getTransactionId(), transaction);
        return transaction;
    }

    @Override
    public Optional<Transaction> findById(String transactionId) {
        if (transactionId == null) return Optional.empty();
        return Optional.ofNullable(transactionStorage.get(transactionId));
    }

    @Override
    public List<Transaction> findByUserId(String userId) {
        if (userId == null) return Collections.emptyList();
        return transactionStorage.values().stream()
                .filter(t -> userId.equals(t.getUserId()))
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findAll() {
        return transactionStorage.values().stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .collect(Collectors.toList());
    }
}
