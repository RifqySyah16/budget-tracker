package com.devland.finalproject.budget_tracker.transactionhistory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidTransactioTypeException extends RuntimeException {

    public InvalidTransactioTypeException(String message) {
        super(message);
    }
}
