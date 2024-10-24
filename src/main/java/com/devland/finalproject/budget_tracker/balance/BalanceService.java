package com.devland.finalproject.budget_tracker.balance;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final ApplicationUserService applicationUserService;

    public void updateIncomeBalance(ApplicationUser existingUser, BigDecimal amount) {
        BigDecimal increaseBalance = existingUser.getBalance().add(amount);
        existingUser.setBalance(increaseBalance);
        this.applicationUserService.update(existingUser);
    }

    public void updateExpenseBalance(ApplicationUser existingApplicationUser, BigDecimal amount) {
        BigDecimal decreaseBalance = existingApplicationUser.getBalance().subtract(amount);
        existingApplicationUser.setBalance(decreaseBalance);
        this.applicationUserService.update(existingApplicationUser);
    }

}
