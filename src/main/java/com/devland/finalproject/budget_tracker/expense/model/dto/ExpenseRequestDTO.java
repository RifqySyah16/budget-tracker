package com.devland.finalproject.budget_tracker.expense.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.applicationuser.model.dto.UserExpenseRequestDTO;
import com.devland.finalproject.budget_tracker.expense.model.Expense;
import com.devland.finalproject.budget_tracker.expense.model.ExpenseCategory;

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
public class ExpenseRequestDTO {
    private Long id;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Category is required")
    private ExpenseCategory expenseCategory;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @Valid
    private UserExpenseRequestDTO userExpenseRequestDTO;

    public Expense convertToEntity() {
        ApplicationUser applicationUser = this.userExpenseRequestDTO.convertToEntity();

        return Expense.builder()
                .id(this.id)
                .amount(this.amount)
                .expenseCategory(this.expenseCategory)
                .date(this.date)
                .applicationUser(applicationUser)
                .build();
    }
}