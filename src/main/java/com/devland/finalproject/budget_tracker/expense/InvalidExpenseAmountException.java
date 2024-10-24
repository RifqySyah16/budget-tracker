package com.devland.finalproject.budget_tracker.expense;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidExpenseAmountException extends RuntimeException {

    public InvalidExpenseAmountException(String message) {
        super(message);
    }
}
