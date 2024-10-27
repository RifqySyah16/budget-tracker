package com.devland.finalproject.budget_tracker.transactionhistory;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionType;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

        Page<TransactionHistory> findAllByApplicationUserAndDateBetweenAndTransactionType(
                        ApplicationUser applicationUser,
                        LocalDate startDate, LocalDate endDate, TransactionType transactionType, Pageable pageable);

        Page<TransactionHistory> findAllByApplicationUserAndDateBetween(ApplicationUser applicationUser,
                        LocalDate startDate,
                        LocalDate endDate, Pageable pageable);

}
