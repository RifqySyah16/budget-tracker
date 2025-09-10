package com.devland.finalproject.budget_tracker.goal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidGoalProgressException extends RuntimeException {

    public InvalidGoalProgressException(String message) {
        super(message);
    }
}
