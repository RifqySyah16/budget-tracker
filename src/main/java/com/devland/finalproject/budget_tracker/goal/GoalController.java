package com.devland.finalproject.budget_tracker.goal;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devland.finalproject.budget_tracker.applicationuser.ApplicationUserService;
import com.devland.finalproject.budget_tracker.applicationuser.model.ApplicationUser;
import com.devland.finalproject.budget_tracker.authentication.model.UserPrincipal;
import com.devland.finalproject.budget_tracker.goal.model.Goal;
import com.devland.finalproject.budget_tracker.goal.model.dto.GoalRequestDTO;
import com.devland.finalproject.budget_tracker.goal.model.dto.GoalResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goals")
@CrossOrigin(maxAge = 3600)
public class GoalController {
    private final GoalService goalService;
    private final ApplicationUserService applicationUserService;

    @GetMapping
    public ResponseEntity<Page<GoalResponseDTO>> getAll(
            Authentication authentication,
            @RequestParam(value = "name") Optional<String> optionalName,
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        Sort sort = Sort.by(Sort.Direction.valueOf(sortString), orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Goal> pageGoals = this.goalService.getAll(userId, optionalName, pageable);
        Page<GoalResponseDTO> goalResponseDTOs = pageGoals.map(Goal::convertToResponse);

        return ResponseEntity.ok(goalResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> getOne(@PathVariable("id") Long id, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        Goal existingGoal = this.goalService.getOne(id, userId);
        GoalResponseDTO goalResponseDTO = existingGoal.convertToResponse();

        return ResponseEntity.ok(goalResponseDTO);
    }

    @PostMapping
    public ResponseEntity<GoalResponseDTO> create(@RequestBody @Valid GoalRequestDTO goalRequestDTO,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        ApplicationUser applicationUser = this.applicationUserService.getOne(userId);

        Goal newGoal = goalRequestDTO.convertToEntity(applicationUser);

        Goal savedGoal = this.goalService.create(newGoal, userId);
        GoalResponseDTO goalResponseDTO = savedGoal.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(goalResponseDTO);
    }

    @PostMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> incrementProgress(
            @PathVariable("id") Long id,
            @RequestBody GoalRequestDTO goalRequestDTO,
            Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        ApplicationUser applicationUser = this.applicationUserService.getOne(userId);

        Goal updatedGoal = goalRequestDTO.convertToEntity(applicationUser);

        Goal savedGoal = this.goalService.incrementProgress(id, userId, updatedGoal.getProgress());
        GoalResponseDTO goalResponseDTO = savedGoal.convertToResponse();

        return ResponseEntity.ok(goalResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> update(@PathVariable("id") Long id,
            @RequestBody GoalRequestDTO goalRequestDTO, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();
        
        ApplicationUser applicationUser = this.applicationUserService.getOne(userId);

        Goal updatedGoal = goalRequestDTO.convertToEntity(applicationUser);
        updatedGoal.setId(id);
        Goal savedGoal = this.goalService.update(updatedGoal, userId);
        GoalResponseDTO goalResponseDTO = savedGoal.convertToResponse();

        return ResponseEntity.ok(goalResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        this.goalService.delete(id, userId);

        return ResponseEntity.ok().build();
    }
}
