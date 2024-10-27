package com.devland.finalproject.budget_tracker.income.model;

import com.devland.finalproject.budget_tracker.income.InvalidCategoryException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IncomeCategory {
    SALARY("Salary"),
    EXTRA_INCOME("Extra Income"),
    INVESTMENT("Investment");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static IncomeCategory fromValue(String value) {
        for (IncomeCategory category : values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new InvalidCategoryException("Invalid Income Category: " + value);
    }
}
