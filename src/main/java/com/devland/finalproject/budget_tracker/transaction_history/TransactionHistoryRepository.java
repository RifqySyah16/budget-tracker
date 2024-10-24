package com.devland.finalproject.budget_tracker.transaction_history;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devland.finalproject.budget_tracker.transaction_history.model.TransactionHistory;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long>{

}
