package com.devland.finalproject.budget_tracker.transaction.model.dto;

import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.transaction.model.Transaction;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDto {
    @NotBlank(message = "Category is required")
    private String category;
    @NotNull(message = "Amount is required")
    private int amount;
    @Valid
    private LocalDate date;

    public Transaction toEntity() {
        return Transaction.builder()
                .category(this.category)
                .amount(this.amount)
                .date(this.date)
                .build();
    }
}
