package com.devland.finalproject.budget_tracker.transactionhistory.model;

import com.devland.finalproject.budget_tracker.transactionhistory.InvalidTransactioTypeException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {
    INCOME("Income"),
    EXPENSE("Expense"),
    GOAL("Goal");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TransactionType fromValue(String value) {
        for (TransactionType transactionType : values()) {
            if (transactionType.getValue().equalsIgnoreCase(value)) {
                return transactionType;
            }
        }

        throw new InvalidTransactioTypeException("Invalid Transaction Type" + value);
    }

}
