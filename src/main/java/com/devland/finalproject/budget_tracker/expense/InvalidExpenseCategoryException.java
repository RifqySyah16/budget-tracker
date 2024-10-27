package com.devland.finalproject.budget_tracker.expense;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidExpenseCategoryException extends RuntimeException {

    public InvalidExpenseCategoryException(String message) {
        super(message);
    }
}
