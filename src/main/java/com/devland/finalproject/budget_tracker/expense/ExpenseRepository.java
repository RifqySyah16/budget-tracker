package com.devland.finalproject.budget_tracker.expense;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devland.finalproject.budget_tracker.expense.model.Expense;
import com.devland.finalproject.budget_tracker.expense.model.ExpenseCategory;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findAllByApplicationUserIdAndExpenseCategory(Long userId, ExpenseCategory expenseCategory,
            Pageable pageable);

    Page<Expense> findAllByApplicationUserId(Long userId, Pageable pageable);

}
