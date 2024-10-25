package com.devland.finalproject.budget_tracker.transaction.model.dto;

import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.transaction.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDto {
    private String category;
    private int amount;
    private LocalDate date;

    public Transaction toEntity() {
        return Transaction.builder()
                .category(this.category)
                .amount(this.amount)
                .date(this.date)
                .build();
    }
}
