package com.devland.finalproject.budget_tracker.income;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.authentication.model.UserPrincipal;
import com.devland.finalproject.budget_tracker.income.model.Income;
import com.devland.finalproject.budget_tracker.income.model.IncomeCategory;
import com.devland.finalproject.budget_tracker.income.model.dto.IncomeRequestDTO;
import com.devland.finalproject.budget_tracker.income.model.dto.IncomeResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {
    private final IncomeService incomeService;
    private final ApplicationUserService applicationUserService;

    @GetMapping
    public ResponseEntity<Page<IncomeResponseDTO>> getAll(
            Authentication authentication,
            @RequestParam(value = "category", required = false) Optional<IncomeCategory> optionalCategory,
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        Sort sort = Sort.by(Sort.Direction.valueOf(sortString), orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Income> pageIncome = this.incomeService.getAll(userId, optionalCategory, pageable);
        Page<IncomeResponseDTO> incomeResponseDTOs = pageIncome.map(Income::convertToResponse);

        return ResponseEntity.ok(incomeResponseDTOs);
    }

    @PostMapping
    public ResponseEntity<IncomeResponseDTO> add(@RequestBody @Valid IncomeRequestDTO incomeRequestDTO,
            Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        Income newiIncome = incomeRequestDTO.convertToEntity();
        newiIncome.setApplicationUser(this.applicationUserService.getOne(userId));

        Income savedIncome = this.incomeService.add(newiIncome, userId);
        IncomeResponseDTO incomeResponseDTO = savedIncome.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(incomeResponseDTO);
    }
}
