package com.github.cowwoc.requirements10.test.sample;

import com.github.cowwoc.requirements10.java.MultipleFailuresException;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;
import java.util.StringJoiner;

/**
 * A class that represents a bank account
 */
public final class BankAccountTest
{
	private final Logger log = LoggerFactory.getLogger(BankAccountTest.class);

	@Test
	public void preconditions()
	{
		try
		{
			new BankAccount(-100);
		}
		catch (IllegalArgumentException e)
		{
			log.info("Precondition failed: " + e.getMessage());
		}
	}

	@Test
	public void postconditions()
	{
		try
		{
			BankAccount b = new BankAccount(100);
			b.invariantEnabled = false;
			b.preconditionEnabled = false;
			b.withdraw(150);
		}
		catch (AssertionError e)
		{
			log.info("Postcondition failed: " + e.getMessage());
		}
	}

	@Test
	public void invariants()
	{
		try
		{
			BankAccount b = new BankAccount(100);
			// Hacker steals money
			b.preconditionEnabled = false;
			b.postconditionEnabled = false;
			b.invariantEnabled = false;
			b.withdraw(200);

			b.invariantEnabled = true;
			b.postconditionEnabled = true;
			b.withdraw(100);
		}
		catch (AssertionError e)
		{
			log.info("Invariant failed: " + e.getMessage());
		}
	}

	@Test
	public void multipleFailures()
	{
		try
		{
			BankAccount b = new BankAccount(BankAccount.ONE_MILLION + 500);
			b.withdraw(900);
		}
		catch (MultipleFailuresException e)
		{
			// Catch the assertion error and print the message
			StringJoiner joiner = new StringJoiner("\n");
			joiner.add("Multiple failures:");
			List<ValidationFailure> failures = e.getFailures();
			for (int i = 0; i < failures.size(); ++i)
				joiner.add((i + 1) + ". " + failures.get(i).getMessage());
			log.info(joiner.toString());
		}
	}
}