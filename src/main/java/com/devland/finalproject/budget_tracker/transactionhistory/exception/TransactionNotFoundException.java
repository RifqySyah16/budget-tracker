package com.devland.finalproject.budget_tracker.transactionhistory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
