package com.devland.finalproject.budget_tracker.transactionhistory.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.applicationuser.model.dto.RegisterationResponseDTO;
import com.devland.finalproject.budget_tracker.expense.model.Expense;
import com.devland.finalproject.budget_tracker.expense.model.dto.ExpenseResponseDTO;
import com.devland.finalproject.budget_tracker.goal.model.Goal;
import com.devland.finalproject.budget_tracker.goal.model.dto.GoalResponseDTO;
import com.devland.finalproject.budget_tracker.income.model.Income;
import com.devland.finalproject.budget_tracker.income.model.dto.IncomeResponseDTO;
import com.devland.finalproject.budget_tracker.transactionhistory.model.dto.TransactionHistoryResponseDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private BigDecimal amount;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "income_id")
    private Income income;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser applicationUser;

    public TransactionHistoryResponseDTO convertToResponse() {
        IncomeResponseDTO incomeResponseDTO = this.income != null ? this.income.convertToResponse() : null;
        ExpenseResponseDTO expenseResponseDTO = this.expense != null ? this.expense.convertToResponse() : null;
        GoalResponseDTO goalResponseDTO = this.goal != null ? this.goal.convertToResponse() : null;
        RegisterationResponseDTO registerationResponseDTO = this.applicationUser.convertToResponse();

        return TransactionHistoryResponseDTO.builder()
                .id(this.id)
                .transactionType(this.transactionType)
                .amount(this.amount)
                .date(this.date)
                .incomeResponseDTO(incomeResponseDTO)
                .expenseResponseDTO(expenseResponseDTO)
                .goalResponseDTO(goalResponseDTO)
                .registerationResponseDTO(registerationResponseDTO)
                .build();
    }
}
