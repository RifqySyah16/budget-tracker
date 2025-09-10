package com.devland.finalproject.budget_tracker.applicationuser.model;

import java.math.BigDecimal;
import java.util.List;

import com.devland.finalproject.budget_tracker.applicationuser.model.dto.RegisterationResponseDTO;
import com.devland.finalproject.budget_tracker.balance.Balance;
import com.devland.finalproject.budget_tracker.expense.model.Expense;
import com.devland.finalproject.budget_tracker.goal.model.Goal;
import com.devland.finalproject.budget_tracker.income.model.Income;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;

    @Embedded
    private Balance balance;

    @OneToMany
    private List<Income> incomes;

    @OneToMany
    private List<Expense> expenses;

    @OneToMany
    private List<Goal> goals;

    @OneToMany
    private List<TransactionHistory> transactionHistories;

    public RegisterationResponseDTO convertToResponse() {
        return RegisterationResponseDTO.builder()
                .id(this.id)
                .email(this.email)
                .username(this.username)
                .balance(this.balance != null ? this.balance.getAmount() : BigDecimal.ZERO)
                .build();
    }

    @PrePersist
    public void initializeBalance() {
        if (this.balance == null) {
            this.balance = Balance.zero();
        }
    }
}
