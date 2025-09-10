package com.devland.finalproject.budget_tracker.balance;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NegativeBalanceIncreaseException extends RuntimeException{

    public NegativeBalanceIncreaseException(String message) {
        super(message);
    }
}
