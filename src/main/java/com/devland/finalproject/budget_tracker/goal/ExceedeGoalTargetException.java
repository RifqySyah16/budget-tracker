package com.devland.finalproject.budget_tracker.goal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ExceedeGoalTargetException extends RuntimeException {

    public ExceedeGoalTargetException(String message) {
        super(message);
    }
}
