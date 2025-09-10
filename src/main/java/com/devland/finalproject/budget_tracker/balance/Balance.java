package com.devland.finalproject.budget_tracker.balance;

import java.math.BigDecimal;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Balance {
    private BigDecimal amount;

    public static Balance zero() {
        return new Balance(BigDecimal.ZERO);
    }

    public Balance increase(BigDecimal value) {
        this.validatePositive(value, "Balance Increase value must be positive");

        return new Balance(this.amount.add(value));
    }

    public Balance decrease(BigDecimal value) {
        this.validatePositive(value, "Balance Increase value must be positive");
        
        if (this.amount.compareTo(value) < 0) {
            throw new InsufficientBalanceException("Not enough balance");
        }

        return new Balance(this.amount.subtract(value));
    }

    private void validatePositive(BigDecimal value, String message) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeBalanceIncreaseException(message);
        }
    }
}
