package com.devland.finalproject.budget_tracker.income;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidAmoutException extends RuntimeException {

    public InvalidAmoutException(String message) {
        super(message);
    }
}
