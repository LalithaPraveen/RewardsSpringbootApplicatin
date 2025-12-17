package com.example.rewards.RewardsApplication.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.example.rewards.RewardsApplication.model.Transaction;
import com.example.rewards.RewardsApplication.service.RewardsService;

public class RewardsServiceTest {

	private final RewardsService rewardsService = new RewardsService();

	@Test
	public void testRewardCalculation() {

		var transactions = Arrays.asList(new Transaction("ram", 120, LocalDate.of(2025, 1, 1)));

		var response = rewardsService.calculateRewards(transactions);

		assertEquals(90, response.get(0).getTotalPoints());
	}

	@Test
	public void testZeroRewards() {

		var transactions = Arrays.asList(new Transaction("raja", 40, LocalDate.now()));

		var response = rewardsService.calculateRewards(transactions);

		assertEquals(0, response.get(0).getTotalPoints());
	}
}
