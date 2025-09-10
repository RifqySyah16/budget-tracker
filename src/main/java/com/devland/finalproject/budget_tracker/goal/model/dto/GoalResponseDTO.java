package com.devland.finalproject.budget_tracker.goal.model.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.devland.finalproject.budget_tracker.applicationuser.model.dto.RegisterationResponseDTO;

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
public class GoalResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal progress;
    private BigDecimal target;
    private Timestamp date;
    private RegisterationResponseDTO registerationResponseDTO;
}
