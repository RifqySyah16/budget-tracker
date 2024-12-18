package com.devland.finalproject.budget_tracker.transactionhistory.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.applicationuser.model.dto.UserResponseDTO;
import com.devland.finalproject.budget_tracker.expense.model.dto.ExpenseResponseDTO;
import com.devland.finalproject.budget_tracker.goal.model.dto.GoalResponseDTO;
import com.devland.finalproject.budget_tracker.income.model.dto.IncomeResponseDTO;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryResponseDTO {
    private Long id;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDate date;
    private UserResponseDTO userResponseDTO;
    private IncomeResponseDTO incomeResponseDTO;
    private ExpenseResponseDTO expenseResponseDTO;
    private GoalResponseDTO goalResponseDTO;
}
