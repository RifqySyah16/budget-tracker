package com.devland.finalproject.budget_tracker.transactionhistory.model.dto;

import java.math.BigDecimal;

import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.applicationuser.model.dto.RegisterationRequestDTO;
import com.devland.finalproject.budget_tracker.expense.model.Expense;
import com.devland.finalproject.budget_tracker.expense.model.dto.ExpenseRequestDTO;
import com.devland.finalproject.budget_tracker.income.model.Income;
import com.devland.finalproject.budget_tracker.income.model.dto.IncomeRequestDTO;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionType;

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
public class TransactionHistoryRequestDTO {
    private Long id;

    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @Valid
    private IncomeRequestDTO incomeRequestDTO;

    @Valid
    private ExpenseRequestDTO expenseRequestDTO;

    @Valid
    private RegisterationRequestDTO registerationRequestDTO;

    public TransactionHistory convertToEntity() {
        Income income = this.incomeRequestDTO.convertToEntity();
        Expense expense = this.expenseRequestDTO.convertToEntity();
        ApplicationUser applicationUser = this.registerationRequestDTO.convertToEntity();

        return TransactionHistory.builder()
                .id(this.id)
                .transactionType(this.transactionType)
                .amount(this.amount)
                .income(income)
                .expense(expense)
                .applicationUser(applicationUser)
                .build();
    }
}
