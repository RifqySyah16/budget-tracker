package com.devland.finalproject.budget_tracker.applicationuser.model.dto;

import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserExpenseRequestDTO {
    private Long id;

    public ApplicationUser convertToEntity() {
        return ApplicationUser.builder()
                .id(this.id)
                .build();
    }
}
