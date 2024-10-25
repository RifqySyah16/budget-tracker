package com.devland.finalproject.budget_tracker.goal;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.balance.BalanceService;
import com.devland.finalproject.budget_tracker.goal.model.Goal;
import com.devland.finalproject.budget_tracker.transactionhistory.TransactionHistoryService;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalRepository goalRepository;
    private final BalanceService balanceService;
    private final ApplicationUserService applicationUserService;
    private final TransactionHistoryService transactionHistoryService;

    public Page<Goal> getAll(Long userId, Optional<Goal> optionalName, Pageable pageable) {
        if (optionalName.isPresent()) {
            return this.goalRepository.findAllByApplicationUserIdAndNameContainsIgnoreCase(userId,
                    optionalName.get(), pageable);
        }

        return this.goalRepository.findAllByApplicationUserId(userId, pageable);
    }

    public Goal getOne(Long id, Long userId) {
        Goal goal = this.goalRepository.findById(id).orElseThrow(() -> new GoalNotFoundException("Goal not found"));
        validationUserById(goal, userId);

        return goal;
    }

    public Goal create(Goal newGoal, Long userId) {
        this.validationUserById(newGoal, userId);

        Optional<Goal> existingGoal = this.goalRepository.findByApplicationUserIdAndName(userId, newGoal.getName());
        if (existingGoal.isPresent()) {
            throw new GoalAlreadyExistException("Goal Already Exist");
        }

        ApplicationUser existingUser = this.applicationUserService.getOne(userId);
        newGoal.setApplicationUser(existingUser);

        this.balanceService.decreaseBalance(existingUser, newGoal.getProgress());

        Goal savedGoal = this.goalRepository.save(newGoal);

        TransactionHistory newTransactionHistory = new TransactionHistory();
        newTransactionHistory.setApplicationUser(savedGoal.getApplicationUser());
        newTransactionHistory.setAmount(savedGoal.getProgress());
        newTransactionHistory.setDate(savedGoal.getDate());
        newTransactionHistory.setTransactionType(TransactionType.GOAL);
        newTransactionHistory.setGoal(savedGoal);

        this.transactionHistoryService.add(newTransactionHistory);

        return savedGoal;
    }

    public Goal incrementProgress(Long id, Long userId, Goal updatedGoal) {
        Goal existingGoal = this.getOne(id, userId);

        this.balanceService.decreaseBalance(existingGoal.getApplicationUser(), updatedGoal.getProgress());

        Goal savedGoal = this.goalRepository.save(updatedGoal);

        TransactionHistory newTransactionHistory = new TransactionHistory();
        newTransactionHistory.setApplicationUser(savedGoal.getApplicationUser());
        newTransactionHistory.setAmount(savedGoal.getProgress());
        newTransactionHistory.setDate(savedGoal.getDate());
        newTransactionHistory.setTransactionType(TransactionType.GOAL);
        newTransactionHistory.setGoal(savedGoal);

        this.transactionHistoryService.add(newTransactionHistory);

        return savedGoal;
    }

    public Goal update(Goal updatedGoal, Long userId) {
        Goal existingGoal = this.getOne(updatedGoal.getId(), userId);
        updatedGoal.setId(existingGoal.getId());

        return this.goalRepository.save(updatedGoal);
    }

    public void delete(Long id, Long userId) {
        Goal existingGoal = this.getOne(id, userId);
        this.goalRepository.deleteById(existingGoal.getId());
    }

    private void validationUserById(Goal goal, Long userId) {
        if (goal.getApplicationUser().getId().equals(userId)) {
            throw new AccessGoalDeniedException("User cannot add goal for another user");
        }
    }
}
