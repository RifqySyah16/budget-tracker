package com.devland.finalproject.budget_tracker.goal.model;

import java.math.BigDecimal;

import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.applicationuser.model.dto.RegisterationResponseDTO;
import com.devland.finalproject.budget_tracker.goal.model.dto.GoalResponseDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal progress;

    private BigDecimal target;

    @ManyToOne
    private ApplicationUser applicationUser;

    public GoalResponseDTO convertToResponse() {
        RegisterationResponseDTO registerationResponseDTO = this.applicationUser.convertToResponse();

        return GoalResponseDTO.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .progress(this.progress)
                .target(this.target)
                .registerationResponseDTO(registerationResponseDTO)
                .build();
    }
}
