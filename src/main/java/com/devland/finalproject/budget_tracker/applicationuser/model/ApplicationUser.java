package com.devland.finalproject.budget_tracker.applicationuser.model;

import java.math.BigDecimal;
import java.util.List;

import com.devland.finalproject.budget_tracker.applicationuser.model.dto.RegisterationResponseDTO;
import com.devland.finalproject.budget_tracker.applicationuser.model.dto.UserResponseDTO;
import com.devland.finalproject.budget_tracker.expense.model.Expense;
import com.devland.finalproject.budget_tracker.goal.model.Goal;
import com.devland.finalproject.budget_tracker.income.model.Income;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

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
                .name(this.name)
                .email(this.email)
                .username(this.username)
                .password(password)
                .balance(this.balance)
                .build();
    }

    public UserResponseDTO convertToUserResponse() {
        return UserResponseDTO.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .balance(this.balance)
                .build();
    }
}
