package com.devland.finalproject.budget_tracker.transactionhistory;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;
import com.devland.finalproject.budget_tracker.transactionhistory.model.dto.TransactionHistoryResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction-histories")
public class TransactionHistoryController {
    private final TransactionHistoryService transactionHistoryService;

    @GetMapping
    public ResponseEntity<Page<TransactionHistoryResponseDto>> getAll(
            @RequestParam("category") Optional<String> optionalCategory,
            @RequestParam(value = "start_date", required = true) LocalDate startDate,
            @RequestParam(value = "end_date", required = true) LocalDate endDate,
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortString), orderBy);
        PageRequest pageable = PageRequest.of(page - 1, limit, sort);
        Page<TransactionHistory> transactionHistories = this.transactionHistoryService.findAll(optionalCategory, startDate, endDate, pageable);
        Page<TransactionHistoryResponseDto> transactionHistoryResponseDtos = transactionHistories
                .map(TransactionHistory::toResponse);

        return ResponseEntity.ok(transactionHistoryResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionHistoryResponseDto> findById(@PathVariable("id") Long id) {
        TransactionHistory existingTransactionHistory = this.transactionHistoryService.findById(id);
        TransactionHistoryResponseDto transactionHistoryResponseDto = existingTransactionHistory.toResponse();

        return ResponseEntity.ok(transactionHistoryResponseDto);
    }
}
