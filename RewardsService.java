package com.example.rewards.RewardsApplication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.rewards.RewardsApplication.exception.InvalidTransactionException;
import com.example.rewards.RewardsApplication.model.RewardsResponse;
import com.example.rewards.RewardsApplication.model.Transaction;

import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardsService {

	private static final Logger log = LoggerFactory.getLogger(RewardsService.class);
	/**
	 * Calculates reward points per customer per month and total.
	 */
	public List<RewardsResponse> calculateRewards(List<Transaction> transactions) {

		log.info("Starting reward calculation for {} transactions",
				transactions != null ? transactions.size() : 0);
		if (transactions == null || transactions.isEmpty()) {
			throw new InvalidTransactionException("Transaction list cannot be empty");
		}

		Map<String, List<Transaction>> transactionsByCustomer = transactions.stream()
				.collect(Collectors.groupingBy(Transaction::getCustomerId));
		log.debug("Grouped transactions by customer: {}", transactionsByCustomer.keySet());
		List<RewardsResponse> responses = new ArrayList<>();

		for (Map.Entry<String, List<Transaction>> entry : transactionsByCustomer.entrySet()) {

			String customerId = entry.getKey();

			Map<String, Integer> monthlyPoints = entry.getValue().stream()
					.collect(Collectors.groupingBy(
							tx -> tx.getTransactionDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
							Collectors.summingInt(tx -> calculatePoints(tx.getAmount()))));

			int totalPoints = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();

			responses.add(new RewardsResponse(customerId, monthlyPoints, totalPoints));
		}

		return responses;
	}

	/**
	 * Reward calculation logic
	 */
	private int calculatePoints(double amount) {

		if (amount < 0) {
			throw new InvalidTransactionException("Transaction amount cannot be negative");
		}

		int points = 0;

		if (amount > 100) {
			points += (amount - 100) * 2;
			amount = 100;
		}

		if (amount > 50) {
			points += (amount - 50);
		}

		return points;
	}
}
