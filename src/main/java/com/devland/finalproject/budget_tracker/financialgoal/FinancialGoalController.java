package com.devland.finalproject.budget_tracker.financialgoal;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devland.finalproject.budget_tracker.financialgoal.model.FinancialGoal;
import com.devland.finalproject.budget_tracker.financialgoal.model.dto.FinancialGoalRequestDto;
import com.devland.finalproject.budget_tracker.financialgoal.model.dto.FinancialGoalResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/financial-goals")
public class FinancialGoalController {
    private final FinancialGoalService financialGoalService;

    @GetMapping
    public ResponseEntity<Page<FinancialGoalResponseDto>> findAll(
            @RequestParam("title") Optional<String> optionalTitle,
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortString), orderBy);
        PageRequest pageable = PageRequest.of(page - 1, limit, sort);
        Page<FinancialGoal> pageBooks = this.financialGoalService.findAll(optionalTitle, pageable);
        Page<FinancialGoalResponseDto> financialGoalResponseDtos = pageBooks.map(FinancialGoal::toResponse);

        return ResponseEntity.ok(financialGoalResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialGoalResponseDto> findById(@PathVariable("id") Long id) {
        FinancialGoal existingFinancialGoal = this.financialGoalService.findById(id);
        FinancialGoalResponseDto financialGoalResponseDto = existingFinancialGoal.toResponse();

        return ResponseEntity.ok(financialGoalResponseDto);
    }

    @PostMapping
    public ResponseEntity<FinancialGoalResponseDto> create(
            @RequestBody @Valid FinancialGoalRequestDto financialGoalRequestDto) {
        FinancialGoal newFinancialGoal = financialGoalRequestDto.toEntity();
        FinancialGoal savedFinancialGoal = this.financialGoalService.add(newFinancialGoal);
        FinancialGoalResponseDto financialGoalResponseDto = savedFinancialGoal.toResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(financialGoalResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialGoalResponseDto> update(@PathVariable("id") Long id,
            @RequestBody FinancialGoalRequestDto financialGoalRequestDto) {
        FinancialGoal updatedFinancialGoal = financialGoalRequestDto.toEntity();
        updatedFinancialGoal.setId(id);
        FinancialGoal savedFinancialGoal = this.financialGoalService.update(updatedFinancialGoal);
        FinancialGoalResponseDto financialGoalResponseDto = savedFinancialGoal.toResponse();

        return ResponseEntity.ok(financialGoalResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        this.financialGoalService.delete(id);

        return ResponseEntity.ok().build();
    }
}
