package com.devland.finalproject.budget_tracker.income;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.income.model.Income;
import com.devland.finalproject.budget_tracker.income.model.IncomeCategory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final ApplicationUserService applicationUserService;

    public Page<Income> getAll(Long userId, Optional<IncomeCategory> optionalCategory, Pageable pageable) {
        if (optionalCategory.isPresent()) {
            return this.incomeRepository.findAllByApplicationUser_IdAndIncomeCategory(userId, optionalCategory.get(),
                    pageable);
        }

        return this.incomeRepository.findAllByApplicationUser_Id(userId, pageable);
    }

    public Income add(Income newiIncome, Long userId) {
        if (!newiIncome.getApplicationUser().getId().equals(userId)) {
            throw new AccessDeniedException("User cannot add income for another user.");
        }

        ApplicationUser existingUser = this.applicationUserService.getOne(userId);
        newiIncome.setApplicationUser(existingUser);

        return this.incomeRepository.save(newiIncome);
    }
}
