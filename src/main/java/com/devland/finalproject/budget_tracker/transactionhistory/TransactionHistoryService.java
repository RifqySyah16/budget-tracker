package com.devland.finalproject.budget_tracker.transactionhistory;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {
    private final ApplicationUserService applicationUserService;
    private final TransactionHistoryRepository transactionHistoryRepository;

    public Page<TransactionHistory> getAll(Long userId, Optional<TransactionType> optionalTransactionType,
            LocalDate startDate, LocalDate endDate, Pageable pageable) {
        ApplicationUser applicationUser = this.applicationUserService.getOne(userId);

        TransactionType transactionType = optionalTransactionType.isPresent() ? optionalTransactionType.get() : null;

        if (transactionType != null) {
            return this.transactionHistoryRepository.findAllByApplicationUserAndDateBetweenAndTransactionType(
                    applicationUser,
                    startDate, endDate,
                    transactionType,
                    pageable);
        }

        return transactionHistoryRepository.findAllByApplicationUserAndDateBetween(applicationUser, startDate, endDate,
                pageable);
    }

    public void add(TransactionHistory newTransactionHistory) {
        this.transactionHistoryRepository.save(newTransactionHistory);
    }
}
