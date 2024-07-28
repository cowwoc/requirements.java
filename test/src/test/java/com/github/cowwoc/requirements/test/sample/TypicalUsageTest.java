package com.github.cowwoc.requirements.test.sample;

import com.github.cowwoc.requirements.java.MultipleFailuresException;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidators;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

import static com.github.cowwoc.requirements.java.DefaultJavaValidators.assumeThat;
import static com.github.cowwoc.requirements.java.DefaultJavaValidators.checkIf;
import static com.github.cowwoc.requirements.java.DefaultJavaValidators.requireThat;
import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

public final class TypicalUsageTest
{
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void precondition()
	{
		String[] args = new String[0];
		requireThat(args, "args").length().isPositive();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void invariantOrPostCondition()
	{
		String[] args = new String[]{"planet"};

		// Precondition
		requireThat(args, "args").length().isPositive();

		// Class invariant or method postcondition
		assert assumeThat(args[0], "args[0]").isEqualTo("world").elseThrow();
	}

	@Test(expectedExceptions = MultipleFailuresException.class)
	public void multipleFailures()
	{
		String[] args = new String[]{"world"};

		// Precondition
		requireThat(args, "args").length().isPositive();

		// Class invariant or method postcondition
		assert assumeThat(args[0], "args[0]").isEqualTo("world").elseThrow();

		// Throwing multiple validation failures at once
		checkIf(args, "args").isEmpty().
			and(checkIf(args[0], "args[0]").isEqualTo("planet")).
			elseThrow();
	}

	@Test
	public void context()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Duration duration = Duration.ofDays(1);
			Set<Duration> bucket = Set.of();

			TestValidators validators = new TestValidatorsImpl(scope);
			validators.requireThat(duration, "duration").isGreaterThan(Duration.ofDays(0));
			try
			{
				validators.requireThat(bucket, "bucket").
					withContext("SomeContext", "SomeName").
					contains(duration);
			}
			catch (IllegalArgumentException e)
			{
				if (!e.getMessage().contains("SomeName") || !e.getMessage().contains("SomeContext"))
					throw new AssertionError(e);
			}
		}
	}

	/**
	 * Demonstrate the ability to statically import the same method name from different modules.
	 */
	@Test
	public void validatorsFromMultipleModules()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Map<Integer, Integer> map = Map.of(1, 5);
			Multimap<Integer, Integer> multimap = ImmutableMultimap.of(1, 5, 1, 6);

			// Java will invoke JavaValidators.requireThat() or GuavaValidators.requireThat() depending on the
			// context
			validators.requireThat(map, "map").size().isPositive();
			validators.requireThat(multimap, "multimap").entries().containsAll(ImmutableList.of(
				Maps.immutableEntry(1, 5), Maps.immutableEntry(1, 6)));

			// Assertions work too
			assert validators.checkIf(multimap, "multimap").size().isPositive().elseThrow();
		}
	}

	@Test(expectedExceptions = AssertionError.class)
	public void namelessValidation()
	{
		int actual = 5;
		assumeThat(actual).isEqualTo(6);
	}
}