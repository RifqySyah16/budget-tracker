package com.devland.finalproject.budget_tracker.financialgoal.model;

import java.util.List;

import com.devland.finalproject.budget_tracker.financialgoal.model.dto.FinancialGoalResponseDto;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class FinancialGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long goal;
    @Builder.Default
    private Long progress = (long) 0;

    @OneToMany(mappedBy = "financialGoal")
    @JsonIgnore
    private List<TransactionHistory> transactionHistories;

    public FinancialGoalResponseDto toResponse() {
        return FinancialGoalResponseDto.builder()
                .id(this.id)
                .title(this.title)
                .goal(this.goal)
                .progress(this.progress)
                .build();
    }
}
