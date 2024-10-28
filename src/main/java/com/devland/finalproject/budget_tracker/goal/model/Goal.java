package com.devland.finalproject.budget_tracker.goal.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.applicationuser.model.dto.UserResponseDTO;
import com.devland.finalproject.budget_tracker.goal.model.dto.GoalResponseDTO;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    private LocalDate date;

    @OneToMany
    private List<TransactionHistory> transactionHistories;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser applicationUser;

    public GoalResponseDTO convertToResponse() {
        UserResponseDTO userResponseDTO = this.applicationUser.convertToUserResponse();

        return GoalResponseDTO.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .progress(this.progress)
                .target(this.target)
                .date(this.date)
                .userResponseDTO(userResponseDTO)
                .build();
    }
}
