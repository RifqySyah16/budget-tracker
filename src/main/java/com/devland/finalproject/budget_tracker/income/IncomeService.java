package com.devland.finalproject.budget_tracker.income;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.income.model.Income;
import com.devland.finalproject.budget_tracker.income.model.IncomeCategory;
import com.devland.finalproject.budget_tracker.transactionhistory.TransactionHistoryService;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final ApplicationUserService applicationUserService;
    private final TransactionHistoryService transactionHistoryService;

    public Page<Income> getAll(Long userId, Optional<IncomeCategory> optionalCategory, Pageable pageable) {
        if (optionalCategory.isPresent()) {
            return this.incomeRepository.findAllByApplicationUserIdAndIncomeCategory(userId, optionalCategory.get(),
                    pageable);
        }

        return this.incomeRepository.findAllByApplicationUserId(userId, pageable);
    }

    public Income add(Income newiIncome, Long userId) {
        this.validateIncomeAmount(newiIncome);
        this.validateIncomeOwnership(newiIncome, userId);

        ApplicationUser existingUser = this.applicationUserService.getOne(userId);
        newiIncome.setApplicationUser(existingUser);

        existingUser.setBalance(existingUser.getBalance().increase(newiIncome.getAmount()));

        Income savedIncome = this.incomeRepository.save(newiIncome);

        this.transactionHistoryService.add(TransactionHistory.fromIncome(savedIncome));

        return savedIncome;
    }

    public void validateIncomeOwnership(Income income, Long userId) {
        if (!income.getApplicationUser().getId().equals(userId)) {
            throw new AccessDeniedException("User cannot add income for another user.");
        }
    }

    private void validateIncomeAmount(Income income) {
        if (income.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidIncomeAmountException("Income amount cannot be zero or negative");
        }
    }
}
