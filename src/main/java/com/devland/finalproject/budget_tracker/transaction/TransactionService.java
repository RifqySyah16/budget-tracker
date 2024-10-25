package com.devland.finalproject.budget_tracker.transaction;

import org.springframework.stereotype.Service;

import com.devland.finalproject.budget_tracker.transaction.model.Transaction;
import com.devland.finalproject.budget_tracker.transactionhistory.TransactionHistoryService;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionHistoryService transactionHistoryService;

    public Transaction create(Transaction newTransaction) {
        // // TODO: Read current budget plan
        // BudgetPlan budgetPlan = this.budgetPlanService.findByCategory(newTransaction.getCategory()); // throw exception if category not found
        // // TODO: Check if the transaction amount exceeds the budget
        // double remainingBudget = budgetPlan.getRemainingBudget();
        // this.isBudgetSufficient(newTransaction, remainingBudget);

        // TODO: Create new transaction history
        TransactionHistory newTransactionHistory = new TransactionHistory();
        newTransactionHistory.setCategory(newTransaction.getCategory());
        newTransactionHistory.setAmount(newTransaction.getAmount());
        newTransactionHistory.setDate(newTransaction.getDate());

        this.transactionHistoryService.save(newTransactionHistory);
        newTransaction.setTransactionHistory(newTransactionHistory);

        // // TODO: Update current budget plan
        // budgetPlan.setRemainingBudget(remainingBudget - newTransaction.getAmount());
        // this.budgetPlanService.update(budgetPlan);

        return this.transactionRepository.save(newTransaction);
    }

    // public void isBudgetSufficient(Transaction newTransaction, double remainingBudget) {
    //     if (newTransaction.getAmount() > remainingBudget) {
    //         throw new BudgetExceededException("Transaction exceeds the remaining budget for the category: " + newTransaction.getCategory());
    //     }
    // }
}
