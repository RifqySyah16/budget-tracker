package com.devland.finalproject.budget_tracker.goal.model.dto;

import java.math.BigDecimal;

import com.devland.finalproject.budget_tracker.goal.model.Goal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoalProgressRequestDTO {
    private BigDecimal additionalProgress;

    public Goal convertToEntity() {
        return Goal.builder().progress(this.additionalProgress).build();
    }
}
