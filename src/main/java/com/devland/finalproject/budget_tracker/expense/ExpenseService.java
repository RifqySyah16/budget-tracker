package com.devland.finalproject.budget_tracker.expense;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.expense.model.Expense;
import com.devland.finalproject.budget_tracker.expense.model.ExpenseCategory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ApplicationUserService applicationUserService;

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

        return this.expenseRepository.save(newExpense);
    }

}
