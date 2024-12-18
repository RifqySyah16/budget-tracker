package com.devland.finalproject.budget_tracker.goal.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.goal.model.Goal;

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
public class GoalRequestDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Amount is required")
    private BigDecimal progress;

    @NotNull(message = "Target is required")
    private BigDecimal target;

    @NotNull(message = "Date is required")
    private LocalDate date;

    public Goal convertToEntity() {
        return Goal.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .progress(this.progress)
                .target(this.target)
                .date(this.date)
                .build();
    }
}
