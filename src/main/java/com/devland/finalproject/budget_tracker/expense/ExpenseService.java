package com.devland.finalproject.budget_tracker.expense;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.expense.model.Expense;
import com.devland.finalproject.budget_tracker.expense.model.ExpenseCategory;
import com.devland.finalproject.budget_tracker.transactionhistory.TransactionHistoryService;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ApplicationUserService applicationUserService;
    private final TransactionHistoryService transactionHistoryService;

    public Page<Expense> getAll(Long userId, Optional<ExpenseCategory> optionalCategory, Pageable pageable) {
        if (optionalCategory.isPresent()) {
            return this.expenseRepository.findAllByApplicationUserIdAndExpenseCategory(userId, optionalCategory.get(),
                    pageable);
        }

        return this.expenseRepository.findAllByApplicationUserId(userId, pageable);
    }

    public Expense add(Long userId, Expense newExpense) {
        this.validateExpenseOwnership(newExpense, userId);
        this.validateExpenseAmount(newExpense);

        ApplicationUser existingUser = this.applicationUserService.getOne(userId);
        newExpense.setApplicationUser(existingUser);

        existingUser.setBalance(existingUser.getBalance().decrease(newExpense.getAmount()));

        Expense savedExpense = this.expenseRepository.save(newExpense);

        this.transactionHistoryService.add(TransactionHistory.fromExpense(savedExpense));

        return savedExpense;
    }

    public void validateExpenseOwnership(Expense expense, Long userId) {
        if (!expense.getApplicationUser().getId().equals(userId)) {
            throw new AccessExpenseDeniedException("User cannot add expense for another user.");
        }
    }

    private void validateExpenseAmount(Expense expense) {
        if (expense.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidExpenseAmountException("Expense amount cannot be zero or negative");
        }
    }
}
