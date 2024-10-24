package com.devland.finalproject.budget_tracker.expense;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AccessExpenseDeniedException extends RuntimeException {

    public AccessExpenseDeniedException(String message) {
        super(message);
    }
}
