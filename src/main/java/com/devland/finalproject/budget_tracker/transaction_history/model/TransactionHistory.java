package com.devland.finalproject.budget_tracker.transaction_history.model;

import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.transaction.model.Transaction;
import com.devland.finalproject.budget_tracker.transaction_history.model.dto.TransactionHistoryResponseDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
