package com.devland.finalproject.budget_tracker.applicationuser.model;

import com.devland.finalproject.budget_tracker.applicationuser.model.dto.RegisterationResponseDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
