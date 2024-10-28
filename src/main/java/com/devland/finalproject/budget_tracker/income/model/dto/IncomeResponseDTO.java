package com.devland.finalproject.budget_tracker.income.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.applicationuser.model.dto.UserResponseDTO;
import com.devland.finalproject.budget_tracker.income.model.IncomeCategory;

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
public class IncomeResponseDTO {
    private Long id;
    private BigDecimal amount;
    private IncomeCategory incomeCategory;
    private LocalDate date;
    private UserResponseDTO userResponseDTO;
}
