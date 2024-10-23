package com.devland.finalproject.budget_tracker.applicationuser.model;

import java.util.List;

import com.devland.finalproject.budget_tracker.applicationuser.model.dto.RegisterationResponseDTO;
import com.devland.finalproject.budget_tracker.income.model.Income;

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
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;

    @OneToMany
    private List<Income> incomes;

    public RegisterationResponseDTO convertToResponse() {
        return RegisterationResponseDTO.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .username(this.username)
                .password(password)
                .build();
    }
}
