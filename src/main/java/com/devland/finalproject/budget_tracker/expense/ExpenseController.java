package com.devland.finalproject.budget_tracker.expense;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.authentication.model.UserPrincipal;
import com.devland.finalproject.budget_tracker.expense.model.Expense;
import com.devland.finalproject.budget_tracker.expense.model.ExpenseCategory;
import com.devland.finalproject.budget_tracker.expense.model.dto.ExpenseRequestDTO;
import com.devland.finalproject.budget_tracker.expense.model.dto.ExpenseResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final ApplicationUserService applicationUserService;

    @GetMapping
    public ResponseEntity<Page<ExpenseResponseDTO>> getAll(
            Authentication authentication,
            @RequestParam(value = "category", required = false) Optional<ExpenseCategory> optionalCategory,
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        Sort sort = Sort.by(Sort.Direction.valueOf(sortString), orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Expense> pageExpense = this.expenseService.getAll(userId, optionalCategory, pageable);
        Page<ExpenseResponseDTO> expenseResponseDTOs = pageExpense.map(Expense::convertToResponse);

        return ResponseEntity.ok(expenseResponseDTOs);
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> add(@RequestBody @Valid ExpenseRequestDTO expenseRequestDTO,
            Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        Expense newExpense = expenseRequestDTO.convertToEntity();
        newExpense.setApplicationUser(this.applicationUserService.getOne(userId));

        Expense savedExpense = this.expenseService.add(userId, newExpense);
        ExpenseResponseDTO expenseResponseDTO = savedExpense.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(expenseResponseDTO);
    }
}
