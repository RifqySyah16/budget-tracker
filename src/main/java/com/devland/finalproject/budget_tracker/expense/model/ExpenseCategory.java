package com.devland.finalproject.budget_tracker.expense.model;

import com.devland.finalproject.budget_tracker.expense.InvalidExpenseCategoryException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExpenseCategory {
    FOOD_DRINK("Food & drinks"),
    RENT("Rent"),
    TRANSPORTATION("Transportation"),
    TELEPHONE_CREDIT("Telephone credit"),
    MEDICAL("Medical");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ExpenseCategory fromValue(String value) {
        for (ExpenseCategory category : values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new InvalidExpenseCategoryException("Invalid Income Category: " + value);
    }
}
