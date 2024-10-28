package com.devland.finalproject.budget_tracker.expense.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.applicationuser.model.dto.UserResponseDTO;
import com.devland.finalproject.budget_tracker.expense.model.ExpenseCategory;

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
public class ExpenseResponseDTO {
    private Long id;
    private BigDecimal amount;
    private ExpenseCategory expenseCategory;
    private LocalDate date;
    private UserResponseDTO userResponseDTO;
}
