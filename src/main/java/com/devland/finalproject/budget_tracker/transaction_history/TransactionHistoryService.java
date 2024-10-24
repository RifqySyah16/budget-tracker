package com.devland.finalproject.budget_tracker.transaction_history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.transaction_history.model.TransactionHistory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {
    private final TransactionHistoryRepository transactionHistoryRepository;

    public Page<TransactionHistory> findAll(Pageable pageable) {
        return this.transactionHistoryRepository.findAll(pageable);
    }

    public TransactionHistory save(TransactionHistory newTransactionHistory) {
        return this.transactionHistoryRepository.save(newTransactionHistory);
    }
}
