package com.devland.finalproject.budget_tracker.income;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devland.finalproject.budget_tracker.income.model.Income;
import com.devland.finalproject.budget_tracker.income.model.IncomeCategory;

public interface IncomeRepository extends JpaRepository<Income, Long> {

        Page<Income> findAllByApplicationUserIdAndIncomeCategory(Long userId, IncomeCategory incomeCategory,
                        Pageable pageable);

        Page<Income> findAllByApplicationUserId(Long userId, Pageable pageable);

}
