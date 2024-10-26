package com.devland.finalproject.budget_tracker.financialgoal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devland.finalproject.budget_tracker.financialgoal.model.FinancialGoal;

public interface FinancialGoalRepository extends JpaRepository<FinancialGoal, Long> {

    Page<FinancialGoal> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

}
