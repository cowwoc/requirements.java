package com.github.cowwoc.requirements10.test.sample;

import com.github.cowwoc.requirements10.java.DefaultJavaValidators;
import com.github.cowwoc.requirements10.java.MultipleFailuresException;
import com.github.cowwoc.requirements10.java.ValidationFailures;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidators;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

import static com.github.cowwoc.requirements10.java.DefaultJavaValidators.checkIf;
import static com.github.cowwoc.requirements10.java.DefaultJavaValidators.requireThat;
import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

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
		assert DefaultJavaValidators.that(args[0], "args[0]").isEqualTo("world").elseThrow();
	}

	@Test(expectedExceptions = MultipleFailuresException.class)
	public void multipleFailures()
	{
		String[] args = new String[]{"world"};

		// Precondition
		requireThat(args, "args").length().isPositive();

		// Class invariant or method postcondition
		assert DefaultJavaValidators.that(args[0], "args[0]").isEqualTo("world").elseThrow();

		// Throwing multiple validation failures at once
		ValidationFailures failures = checkIf(args, "args").isEmpty().elseGetFailures();
		failures.addAll(checkIf(args[0], "args[0]").isEqualTo("planet").elseGetFailures());
		failures.throwOnFailure();
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
		DefaultJavaValidators.that(actual).isEqualTo(6).elseThrow();
	}
}