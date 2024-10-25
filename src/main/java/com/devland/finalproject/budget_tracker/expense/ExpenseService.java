package com.devland.finalproject.budget_tracker.expense;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.balance.BalanceService;
import com.devland.finalproject.budget_tracker.expense.model.Expense;
import com.devland.finalproject.budget_tracker.expense.model.ExpenseCategory;
import com.devland.finalproject.budget_tracker.transactionhistory.TransactionHistoryService;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final BalanceService balanceService;
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
        if (!newExpense.getApplicationUser().getId().equals(userId)) {
            throw new AccessExpenseDeniedException("User cannot add income for another user.");
        }

        ApplicationUser existingApplicationUser = this.applicationUserService.getOne(userId);
        newExpense.setApplicationUser(existingApplicationUser);

        if (newExpense.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidExpenseAmountException("Expense amount cannot be negative");
        }

        this.balanceService.decreaseBalance(existingApplicationUser, newExpense.getAmount());

        Expense savedExpense = this.expenseRepository.save(newExpense);

        TransactionHistory newTransactionHistory = new TransactionHistory();
        newTransactionHistory.setApplicationUser(savedExpense.getApplicationUser());
        newTransactionHistory.setAmount(savedExpense.getAmount());
        newTransactionHistory.setDate(savedExpense.getDate());
        newTransactionHistory.setTransactionType(TransactionType.EXPENSE);
        newTransactionHistory.setExpense(savedExpense);

        this.transactionHistoryService.add(newTransactionHistory);

        return savedExpense;
    }

}
