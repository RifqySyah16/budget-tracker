package com.devland.finalproject.budget_tracker.financialgoal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FinancialGoalNotFoundException extends RuntimeException {
    public FinancialGoalNotFoundException(String message) {
        super(message);
    }
}
