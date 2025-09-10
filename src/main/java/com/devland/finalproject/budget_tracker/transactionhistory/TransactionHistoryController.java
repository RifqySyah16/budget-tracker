package com.devland.finalproject.budget_tracker.transactionhistory;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devland.finalproject.budget_tracker.authentication.model.UserPrincipal;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionHistory;
import com.devland.finalproject.budget_tracker.transactionhistory.model.TransactionType;
import com.devland.finalproject.budget_tracker.transactionhistory.model.dto.TransactionHistoryResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
@RequestMapping("/transaction-histories")
public class TransactionHistoryController {
        private final TransactionHistoryService transactionHistoryService;

        @GetMapping
        public ResponseEntity<Page<TransactionHistoryResponseDTO>> getAll(
                        Authentication authentication,
                        @RequestParam(value = "transaction_type", required = false) Optional<TransactionType> optionalTransactionType,
                        @RequestParam(value = "start_date", required = true) LocalDate startDate,
                        @RequestParam(value = "end_date", required = true) LocalDate endDate,
                        @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
                        @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
                        @RequestParam(value = "limit", defaultValue = "5") int limit,
                        @RequestParam(value = "page", defaultValue = "1") int page) {

                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                Long userId = userPrincipal.getId();

                Sort sort = Sort.by(Sort.Direction.valueOf(sortString), orderBy);
                Pageable pageable = PageRequest.of(page - 1, limit, sort);

                Page<TransactionHistory> pageTransactionHistory = this.transactionHistoryService.getAll(userId,
                                optionalTransactionType, startDate, endDate, pageable);
                Page<TransactionHistoryResponseDTO> transactionHistoryResponseDTOs = pageTransactionHistory
                                .map(TransactionHistory::convertToResponse);

                return ResponseEntity.ok(transactionHistoryResponseDTOs);
        }
}
