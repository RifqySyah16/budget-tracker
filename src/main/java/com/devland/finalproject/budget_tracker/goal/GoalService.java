package com.devland.finalproject.budget_tracker.goal;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.goal.model.Goal;
import com.devland.finalproject.budget_tracker.transactionhistory.TransactionHistoryService;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalRepository goalRepository;
    private final ApplicationUserService applicationUserService;
    private final TransactionHistoryService transactionHistoryService;

    public Page<Goal> getAll(Long userId, Optional<String> optionalName, Pageable pageable) {
        if (optionalName.isPresent()) {
            return this.goalRepository.findAllByApplicationUserIdAndNameContainsIgnoreCase(userId,
                    optionalName.get(), pageable);
        }

        return this.goalRepository.findAllByApplicationUserId(userId, pageable);
    }

    public Goal getOne(Long id, Long userId) {
        Goal goal = this.goalRepository.findById(id).orElseThrow(() -> new GoalNotFoundException("Goal not found"));
        validationUserById(goal, userId);

        this.validationUserById(goal, userId);

        return goal;
    }

    @Transactional
    public Goal create(Goal newGoal, Long userId) {
        ApplicationUser existingUser = this.applicationUserService.getOne(userId);
        newGoal.setApplicationUser(existingUser);

        Optional<Goal> existingGoal = this.goalRepository.findByApplicationUserIdAndName(userId, newGoal.getName());
        if (existingGoal.isPresent()) {
            throw new GoalAlreadyExistException("Goal Already Exist");
        }

        existingUser.setBalance(existingUser.getBalance().decrease(newGoal.getProgress()));

        Goal savedGoal = this.goalRepository.save(newGoal);

        this.transactionHistoryService.add(TransactionHistory.fromGoal(savedGoal));

        return savedGoal;
    }

    public Goal incrementProgress(Long id, Long userId, BigDecimal incrementValue) {
        Goal existingGoal = this.getOne(id, userId);
        ApplicationUser existingUser = existingGoal.getApplicationUser();

        if (incrementValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidGoalProgressException("Progress cannot be zero or negative");
        }

        BigDecimal newProgress = existingGoal.getProgress().add(incrementValue);

        if (newProgress.compareTo(existingGoal.getTarget()) > 0) {
            throw new GoalExceededTargetException("Progress cannot exceed target");
        }

        existingUser.setBalance(existingUser.getBalance().decrease(incrementValue));

        existingGoal.setProgress(newProgress);
        Goal savedGoal = this.goalRepository.save(existingGoal);

        this.transactionHistoryService.add(TransactionHistory.fromGoal(savedGoal));

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
        if (!goal.getApplicationUser().getId().equals(userId)) {
            throw new AccessGoalDeniedException("User cannot add goal for another user");
        }
    }
}
