package com.devland.finalproject.budget_tracker.goal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class GoalCompletedException extends RuntimeException {

    public GoalCompletedException(String message) {
        super(message);
    }
}
