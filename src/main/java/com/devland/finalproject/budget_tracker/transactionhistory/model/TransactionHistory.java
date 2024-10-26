package com.devland.finalproject.budget_tracker.transactionhistory.model;

import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.financialgoal.model.FinancialGoal;
import com.devland.finalproject.budget_tracker.transaction.model.Transaction;
import com.devland.finalproject.budget_tracker.transactionhistory.model.dto.TransactionHistoryResponseDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private int amount;
    private LocalDate date;

    @OneToOne(mappedBy = "transactionHistory")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "financial_goal_id")
    private FinancialGoal financialGoal;

    public TransactionHistoryResponseDto toResponse() {
        return TransactionHistoryResponseDto.builder()
                .id(this.id)
                .category(this.category)
                .amount(this.amount)
                .date(this.date)
                .transaction(this.transaction)
                .build();
    }
}
