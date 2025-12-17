package com.example.rewards.RewardsApplication.controller;

import org.springframework.web.bind.annotation.*;

import com.example.rewards.RewardsApplication.model.RewardsResponse;
import com.example.rewards.RewardsApplication.model.Transaction;
import com.example.rewards.RewardsApplication.service.RewardsService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }
    //method to get the rewards
    @GetMapping
    public List<RewardsResponse> getRewards() {

        List<Transaction> transactions = Arrays.asList(
                new Transaction("ram", 120, LocalDate.of(2025, 1, 10)),
                new Transaction("ram", 75, LocalDate.of(2025, 2, 15)),
                new Transaction("ram", 40, LocalDate.of(2025, 3, 20)),

                new Transaction("raja", 200, LocalDate.of(2025, 1, 5)),
                new Transaction("raja", 90, LocalDate.of(2025, 2, 10))
        );

        return rewardsService.calculateRewards(transactions);
    }
}

