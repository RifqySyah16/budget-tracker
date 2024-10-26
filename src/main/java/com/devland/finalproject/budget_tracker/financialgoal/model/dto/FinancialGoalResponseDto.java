package com.devland.finalproject.budget_tracker.financialgoal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialGoalResponseDto {
    private Long id;
    private String title;
    private Long goal;
    private Long progress;
}
