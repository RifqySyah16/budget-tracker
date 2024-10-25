package com.devland.finalproject.budget_tracker.transaction.model;

import java.time.LocalDate;

import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private int amount;
    private LocalDate date;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_history_id", referencedColumnName = "id")
    private TransactionHistory transactionHistory;

}
