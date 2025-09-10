package com.devland.finalproject.budget_tracker.income.model.dto;

import java.math.BigDecimal;

import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.income.model.Income;
import com.devland.finalproject.budget_tracker.income.model.IncomeCategory;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeRequestDTO {
    private Long id;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Category is required")
    private IncomeCategory incomeCategory;

    public Income convertToEntity(ApplicationUser applicationUser) {
        return Income.builder()
                .id(this.id)
                .amount(this.amount)
                .incomeCategory(this.incomeCategory)
                .applicationUser(applicationUser)
                .build();
    }
}
