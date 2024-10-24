package com.devland.finalproject.budget_tracker.transactionhistory;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.income.model.Income;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {
    private final TransactionHistoryRepository transactionHistoryRepository;

    public Page<TransactionHistory> getAll(Long userId, Optional<TransactionType> optionalTransactionType,
            Pageable pageable) {
        if (optionalTransactionType.isPresent()) {
            return transactionHistoryRepository.findByApplicationUserAndTransactionType(userId,
                    optionalTransactionType.get(), pageable);
        }

        return transactionHistoryRepository.findByApplicationUser(userId, pageable);
    }

    public void add(Income savedIncome) {
        TransactionHistory transactionHistory = TransactionHistory.builder()
                .transactionType(TransactionType.INCOME)
                .amount(savedIncome.getAmount())
                .income(savedIncome)
                .build();

        transactionHistory.setApplicationUser(savedIncome.getApplicationUser());

        this.transactionHistoryRepository.save(transactionHistory);
    }
}
