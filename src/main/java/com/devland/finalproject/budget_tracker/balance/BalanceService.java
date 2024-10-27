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

    public void increaseBalance(ApplicationUser existingUser, BigDecimal amount) {
        BigDecimal currentBalance = existingUser.getBalance().add(amount);
        existingUser.setBalance(currentBalance);
        this.applicationUserService.update(existingUser);
    }

    public void decreaseBalance(ApplicationUser existingApplicationUser, BigDecimal amount) {
        BigDecimal currentBalance = existingApplicationUser.getBalance();
        if (currentBalance.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("Insufficient balance to perform the deduction");
        }
        BigDecimal remaingBalance = currentBalance.subtract(amount);
        existingApplicationUser.setBalance(remaingBalance);
        this.applicationUserService.update(existingApplicationUser);
    }
}
