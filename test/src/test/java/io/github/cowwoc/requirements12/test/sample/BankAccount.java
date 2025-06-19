/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.test.sample;

import io.github.cowwoc.requirements12.java.ValidationFailures;
import io.github.cowwoc.requirements12.test.TestValidators;

/**
 * A class that represents a bank account.
 */
public final class BankAccount
{
	public static final double ONE_MILLION = 1_000_000;
	private final TestValidators validators;
	private double balance;
	public boolean preconditionEnabled = true;
	public boolean postconditionEnabled = true;
	public boolean invariantEnabled = true;

	/**
	 * @param validators     the validators to use
	 * @param initialBalance the account balance
	 * @throws NullPointerException if {@code validators} is null
	 */
	public BankAccount(TestValidators validators, double initialBalance)
	{
		if (validators == null)
			throw new NullPointerException("validators may not be null");
		this.validators = validators;

		validators.requireThat(initialBalance, "initialBalance").isNotNegative();
		balance = initialBalance;
		if (invariantEnabled)
			checkInvariant();
	}

	/**
	 * @param amount the amount of money to withdraw
	 */
	public void withdraw(double amount)
	{
		ValidationFailures failures;
		if (preconditionEnabled)
		{
			failures = validators.checkIf(amount, "amount").isPositive().isLessThanOrEqualTo(balance, "balance").
				elseGetFailures();
		}
		else
			failures = ValidationFailures.EMPTY;
		double oldBalance = balance;
		if (preconditionEnabled)
		{
			balance -= amount;
			if (oldBalance >= ONE_MILLION)
			{
				// Special restrictions for millionaires:
				// * Minimum withdrawal of $1000
				// * The account balance may not drop below $1,000,000
				failures.addAll(validators.checkIf(amount, "amount").isGreaterThan(1000).elseGetFailures());
				failures.addAll(
					validators.checkIf(balance, "balance").isGreaterThanOrEqualTo(ONE_MILLION).elseGetFailures());
			}
			failures.throwOnFailure();
		}
		else
			balance = Math.max(0, balance - amount);
		if (invariantEnabled)
			checkInvariant();
		if (postconditionEnabled)
		{
			assert validators.that(balance, "balance").isEqualTo(oldBalance - amount, "expected").
				elseThrow();
		}
	}

	private void checkInvariant()
	{
		assert validators.that(balance, "balance").isNotNegative().elseThrow();
	}
}