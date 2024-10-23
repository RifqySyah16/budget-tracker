package com.devland.finalproject.budget_tracker.income.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.applicationuser.model.dto.RegisterationResponseDTO;
import com.devland.finalproject.budget_tracker.income.model.dto.IncomeResponseDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private IncomeCategory incomeCategory;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser applicationUser;

    public IncomeResponseDTO convertToResponse() {
        RegisterationResponseDTO registerationResponseDTO = this.applicationUser.convertToResponse();

        return IncomeResponseDTO.builder()
                .id(this.id)
                .amount(this.amount)
                .incomeCategory(this.incomeCategory)
                .date(this.date)
                .registerationResponseDTO(registerationResponseDTO)
                .build();
    }
}
