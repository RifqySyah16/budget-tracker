package com.devland.finalproject.budget_tracker.transaction_history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devland.finalproject.budget_tracker.transaction_history.model.TransactionHistory;
import com.devland.finalproject.budget_tracker.transaction_history.model.dto.TransactionHistoryResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TransactionHistoryController {
    private final TransactionHistoryService transactionHistoryService;

    @GetMapping
    public ResponseEntity<Page<TransactionHistoryResponseDto>> getAll(
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortString), orderBy);
        PageRequest pageable = PageRequest.of(page - 1, limit, sort);
        Page<TransactionHistory> transactionHistories = this.transactionHistoryService.findAll(pageable);
        Page<TransactionHistoryResponseDto> transactionHistoryResponseDtos = transactionHistories
                .map(TransactionHistory::toResponse);

        return ResponseEntity.ok(transactionHistoryResponseDtos);
    }
}
