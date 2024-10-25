package com.devland.finalproject.budget_tracker.transactionhistory;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.transactionhistory.exception.TransactionNotFoundException;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {
    private final TransactionHistoryRepository transactionHistoryRepository;

    public Page<TransactionHistory> findAll(Optional<String> optionalCategory, LocalDate startDate, LocalDate endDate,
            Pageable pageable) {
        String category = optionalCategory.isPresent() ? optionalCategory.get() : null;

        if (category != null) {
            return this.transactionHistoryRepository.findAllByDateBetweenAndCategory(startDate, endDate, category,
                    pageable);
        }

        return this.transactionHistoryRepository.findAllByDateBetween(startDate, endDate, pageable);
    }

    public TransactionHistory findById(Long id) {
        return this.transactionHistoryRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with ID: " + id));
    }

    public TransactionHistory save(TransactionHistory newTransactionHistory) {
        return this.transactionHistoryRepository.save(newTransactionHistory);
    }
}
