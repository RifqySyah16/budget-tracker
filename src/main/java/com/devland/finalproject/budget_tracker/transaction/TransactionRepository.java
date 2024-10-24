package com.devland.finalproject.budget_tracker.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devland.finalproject.budget_tracker.transaction.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
