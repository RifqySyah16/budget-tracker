package com.devland.finalproject.budget_tracker.goal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AccessGoalDeniedException extends RuntimeException {

    public AccessGoalDeniedException(String message) {
        super(message);
    }
}
