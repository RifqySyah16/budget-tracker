package com.devland.finalproject.budget_tracker.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devland.finalproject.budget_tracker.transaction.model.Transaction;
import com.devland.finalproject.budget_tracker.transaction.model.dto.TransactionRequestDto;
import com.devland.finalproject.budget_tracker.transaction_history.model.TransactionHistory;
import com.devland.finalproject.budget_tracker.transaction_history.model.dto.TransactionHistoryResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionHistoryResponseDto> create(
            @Valid @RequestBody TransactionRequestDto transactionRequestDto) {
        Transaction newTransaction = transactionRequestDto.toEntity();
        Transaction savedTransaction = this.transactionService.create(newTransaction);
        TransactionHistory transactionHistory = savedTransaction.getTransactionHistory();
        TransactionHistoryResponseDto transactionHistoryResponseDto = transactionHistory.toResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionHistoryResponseDto);
    }
}
