package com.devland.finalproject.budget_tracker.financialgoal.model.dto;

import com.devland.finalproject.budget_tracker.financialgoal.model.FinancialGoal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinancialGoalRequestDto {
    @NotBlank(message = "Title is required")
    private String title;
    @NotNull(message = "Target fund is required")
    private Long goal;

    public FinancialGoal toEntity() {
        return FinancialGoal.builder()
                .title(this.title)
                .goal(this.goal)
                .build();
    }
}
