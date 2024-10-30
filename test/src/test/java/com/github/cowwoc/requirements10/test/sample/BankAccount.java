package com.github.cowwoc.requirements10.test.sample;

import com.github.cowwoc.requirements10.java.DefaultJavaValidators;
import com.github.cowwoc.requirements10.java.ValidationFailures;

import static com.github.cowwoc.requirements10.java.DefaultJavaValidators.checkIf;
import static com.github.cowwoc.requirements10.java.DefaultJavaValidators.requireThat;

/**
 * A class that represents a bank account.
 */
public final class BankAccount
{
	public static final double ONE_MILLION = 1_000_000;
	private double balance;
	public boolean preconditionEnabled = true;
	public boolean postconditionEnabled = true;
	public boolean invariantEnabled = true;

	/**
	 * @param initialBalance the account balance
	 */
	public BankAccount(double initialBalance)
	{
		requireThat(initialBalance, "initialBalance").isNotNegative();
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
			failures = checkIf(amount, "amount").isPositive().isLessThanOrEqualTo(balance, "balance").
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
				failures.addAll(checkIf(amount, "amount").isGreaterThan(1000).elseGetFailures());
				failures.addAll(checkIf(balance, "balance").isGreaterThanOrEqualTo(ONE_MILLION).elseGetFailures());
			}
			failures.throwOnFailure();
		}
		else
			balance = Math.max(0, balance - amount);
		if (invariantEnabled)
			checkInvariant();
		if (postconditionEnabled)
		{
			assert DefaultJavaValidators.that(balance, "balance").isEqualTo(oldBalance - amount, "expected").
				elseThrow();
		}
	}

	private void checkInvariant()
	{
		assert DefaultJavaValidators.that(balance, "balance").isNotNegative().elseThrow();
	}
}