package com.devland.finalproject.budget_tracker.income.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.applicationuser.model.dto.UserIncomeRequestDTO;
import com.devland.finalproject.budget_tracker.income.model.Income;
import com.devland.finalproject.budget_tracker.income.model.IncomeCategory;

import jakarta.validation.Valid;
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

    @NotNull(message = "Date is required")
    private LocalDate date;

    @Valid
    private UserIncomeRequestDTO userIncomeRequestDTO;

    public Income convertToEntity() {
        ApplicationUser applicationUser = this.userIncomeRequestDTO.convertToEntity();

        return Income.builder()
                .id(this.id)
                .amount(this.amount)
                .incomeCategory(this.incomeCategory)
                .date(this.date)
                .applicationUser(applicationUser)
                .build();
    }
}
