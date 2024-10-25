package com.devland.finalproject.budget_tracker.transaction_history.model.dto;

import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.transaction.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryResponseDto {
    private Long id;
    private String category;
    private int amount;
    private LocalDate date;
    private Transaction transaction;
}
