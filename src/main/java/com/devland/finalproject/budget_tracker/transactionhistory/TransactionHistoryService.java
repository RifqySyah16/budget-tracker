package com.devland.finalproject.budget_tracker.transactionhistory;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.expense.model.Expense;
import com.devland.finalproject.budget_tracker.goal.model.Goal;
import com.devland.finalproject.budget_tracker.income.model.Income;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {
    private final ApplicationUserService applicationUserService;
    private final TransactionHistoryRepository transactionHistoryRepository;

    public Page<TransactionHistory> getAll(
            Long userId,
            Optional<TransactionType> optionalTransactionType,
            Pageable pageable) {

        ApplicationUser applicationUser = this.applicationUserService.getOne(userId);

        if (optionalTransactionType.isPresent()) {
            return transactionHistoryRepository
                    .findAllByApplicationUserAndTransactionType(
                            applicationUser,
                            optionalTransactionType.get(),
                            pageable);
        }

        return transactionHistoryRepository.findAllByApplicationUser(applicationUser, pageable);
    }

    public void add(TransactionHistory newTransactionHistory) {
        this.transactionHistoryRepository.save(newTransactionHistory);
    }

    public void createIncomeHistory(Income savedIncome) {
        TransactionHistory incomeTransactionHistory = new TransactionHistory();
        incomeTransactionHistory.setApplicationUser(savedIncome.getApplicationUser());
        incomeTransactionHistory.setAmount(savedIncome.getAmount());
        incomeTransactionHistory.setTransactionType(TransactionType.INCOME);
        incomeTransactionHistory.setIncome(savedIncome);

        this.add(incomeTransactionHistory);
    }

    public void createExpenseHistory(Expense savedExpense) {
        TransactionHistory expenseTransactionHistory = new TransactionHistory();
        expenseTransactionHistory.setApplicationUser(savedExpense.getApplicationUser());
        expenseTransactionHistory.setAmount(savedExpense.getAmount());
        expenseTransactionHistory.setTransactionType(TransactionType.EXPENSE);
        expenseTransactionHistory.setExpense(savedExpense);

        this.add(expenseTransactionHistory);
    }

    public void createGoalHistory(Goal savedGoal) {
        TransactionHistory goalTransactionHistory = new TransactionHistory();
        goalTransactionHistory.setApplicationUser(savedGoal.getApplicationUser());
        goalTransactionHistory.setAmount(savedGoal.getProgress());
        goalTransactionHistory.setTransactionType(TransactionType.GOAL);
        goalTransactionHistory.setGoal(savedGoal);

        this.add(goalTransactionHistory);
    }

    public void createIncreaseGoalHistory(Goal savedGoal) {
        TransactionHistory increaseGoalTransactionHistory = new TransactionHistory();
        increaseGoalTransactionHistory.setApplicationUser(savedGoal.getApplicationUser());
        increaseGoalTransactionHistory.setAmount(savedGoal.getProgress());
        increaseGoalTransactionHistory.setTransactionType(TransactionType.GOAL);
        increaseGoalTransactionHistory.setGoal(savedGoal);

        this.add(increaseGoalTransactionHistory);
    }
}
