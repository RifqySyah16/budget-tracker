package com.devland.finalproject.budget_tracker.goal;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.applicationuser.balance.BalanceService;
import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.goal.model.Goal;
import com.devland.finalproject.budget_tracker.transactionhistory.TransactionHistoryService;

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
        validationUserById(userId);

        return this.goalRepository.findById(id).orElseThrow(() -> new GoalNotFoundException("Goal not found"));
    }

    public Goal create(Goal newGoal, Long userId) {
        this.validationUserById(userId);

        Optional<Goal> existingGoal = this.goalRepository.findByApplicationUserIdAndName(userId, newGoal.getName());
        if (existingGoal.isPresent()) {
            throw new GoalAlreadyExistException("Goal Already Exist");
        }

        ApplicationUser existingUser = this.applicationUserService.getOne(userId);
        newGoal.setApplicationUser(existingUser);

        this.balanceService.decreaseBalance(existingUser, newGoal.getProgress());

        Goal savedGoal = this.goalRepository.save(newGoal);

        this.transactionHistoryService.createGoalHistory(savedGoal);

        return savedGoal;
    }

    public Goal incrementProgress(Long id, Long userId, Goal updatedGoal) {
        Goal existingGoal = this.getOne(id, userId);
        this.validationUserById(userId);

        BigDecimal newProgress = existingGoal.getProgress().add(updatedGoal.getProgress());
        if (newProgress.compareTo(existingGoal.getTarget()) >= 0) {
            throw new ExceedeGoalTargetException("Goal progress has reached or exceeded the target amount.");
        }

        existingGoal.setProgress(newProgress);

        this.balanceService.decreaseBalance(existingGoal.getApplicationUser(), updatedGoal.getProgress());

        ApplicationUser existingUser = this.applicationUserService.getOne(userId);
        updatedGoal.setApplicationUser(existingUser);

        Goal savedGoal = this.goalRepository.save(existingGoal);

        this.transactionHistoryService.createIncreaseGoalHistory(savedGoal);

        return savedGoal;
    }

    public Goal update(Goal updatedGoal, Long userId) {
        Goal existingGoal = this.getOne(updatedGoal.getId(), userId);
        updatedGoal.setId(existingGoal.getId());

        return this.goalRepository.save(updatedGoal);
    }

    public void delete(Long id, Long userId) {
        this.getOne(id, userId);
        this.goalRepository.deleteById(id);
    }

    private void validationUserById(Long userId) {
        if (userId == null) {
            throw new AccessGoalDeniedException("User cannot add goal for another user");
        }
    }
}
