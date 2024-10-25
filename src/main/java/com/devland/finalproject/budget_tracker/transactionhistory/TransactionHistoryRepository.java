package com.devland.finalproject.budget_tracker.transactionhistory;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    Page<TransactionHistory> findAllByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<TransactionHistory> findAllByDateBetweenAndCategory(LocalDate startDate, LocalDate endDate, String category,
            Pageable pageable);

}
