package com.devland.finalproject.budget_tracker.financialgoal;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.financialgoal.exception.FinancialGoalNotFoundException;
import com.devland.finalproject.budget_tracker.financialgoal.model.FinancialGoal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinancialGoalService {
    private final FinancialGoalRepository financialGoalRepository;

    public Page<FinancialGoal> findAll(Optional<String> optionalTitle, Pageable pageable) {
        String title = optionalTitle.isPresent() ? optionalTitle.get() : null;

        if (title != null) {
            return this.financialGoalRepository.findAllByTitleContainingIgnoreCase(title, pageable);
        }

        return this.financialGoalRepository.findAll(pageable);
    }

    public FinancialGoal findById(Long id) {
        return this.financialGoalRepository.findById(id)
                .orElseThrow(() -> new FinancialGoalNotFoundException("Financial goal not found with ID: " + id));
    }

    public FinancialGoal add(FinancialGoal newFinancialGoal) {
        return this.financialGoalRepository.save(newFinancialGoal);
    }

    public FinancialGoal update(FinancialGoal updatedFinancialGoal) {
        FinancialGoal existingFinancialGoal = this.findById(updatedFinancialGoal.getId());
        updatedFinancialGoal.setId(existingFinancialGoal.getId());

        return this.financialGoalRepository.save(updatedFinancialGoal);
    }

    public void delete(Long id) {
        FinancialGoal existingFinancialGoal = this.findById(id);
        this.financialGoalRepository.deleteById(existingFinancialGoal.getId());
    }
}
