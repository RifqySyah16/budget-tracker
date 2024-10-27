package com.devland.finalproject.budget_tracker.goal;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devland.finalproject.budget_tracker.goal.model.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    Page<Goal> findAllByApplicationUserIdAndNameContainsIgnoreCase(Long userId, Goal goal, Pageable pageable);

    Page<Goal> findAllByApplicationUserId(Long userId, Pageable pageable);

    Optional<Goal> findByApplicationUserIdAndName(Long userId, String name);

}
