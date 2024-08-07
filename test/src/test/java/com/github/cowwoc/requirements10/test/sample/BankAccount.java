package com.github.cowwoc.requirements10.test.sample;

import com.github.cowwoc.requirements10.java.DefaultJavaValidators;
import com.github.cowwoc.requirements10.java.internal.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.internal.JavaValidators;

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
	private final JavaValidators multipleAsserts = JavaValidators.newInstance();

	/**
	 * @param initialBalance the account balance
	 */
	public BankAccount(double initialBalance)
	{
		requireThat(initialBalance, "initialBalance").isNotNegative();
		balance = initialBalance;
		if (invariantEnabled)
			checkInvariant();

		try (ConfigurationUpdater config = multipleAsserts.updateConfiguration())
		{
			config.exceptionTransformer(t ->
			{
				AssertionError replacement = new AssertionError(t.getMessage(), t.getCause());
				for (Throwable suppressed : t.getSuppressed())
					replacement.addSuppressed(suppressed);
				return replacement;
			});
		}
	}

	/**
	 * @param amount the amount of money to withdraw
	 */
	public void withdraw(double amount)
	{
		if (preconditionEnabled)
			requireThat(amount, "amount").isPositive().isLessThanOrEqualTo(balance, "balance");
		double oldBalance = balance;
		if (preconditionEnabled)
			balance -= amount;
		else
			balance = Math.max(0, balance - amount);
		if (invariantEnabled)
			checkInvariant();
		if (postconditionEnabled)
			assert DefaultJavaValidators.that(balance, "balance").isEqualTo(oldBalance - amount, "expected")
				.elseThrow();
		if (oldBalance >= ONE_MILLION)
		{
			// Special restrictions for millionaires:
			// * Minimum withdrawal of $1000
			// * Account balance may not less than $1,000,000
			assert multipleAsserts.checkIf(amount, "amount").isGreaterThan(1000).
				and(multipleAsserts.checkIf(balance, "balance").isGreaterThanOrEqualTo(ONE_MILLION)).
				elseThrow();
		}
	}

	private void checkInvariant()
	{
		assert DefaultJavaValidators.that(balance, "balance").isNotNegative().elseThrow();
	}
}