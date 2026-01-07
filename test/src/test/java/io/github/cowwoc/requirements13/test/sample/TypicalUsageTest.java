/*
 * Copyright (c) 2023 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */

package io.github.cowwoc.requirements13.test.sample;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import io.github.cowwoc.requirements13.java.MultipleFailuresException;
import io.github.cowwoc.requirements13.java.ValidationFailures;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.test.TestValidators;
import io.github.cowwoc.requirements13.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

import static io.github.cowwoc.requirements13.java.TerminalEncoding.NONE;

public final class TypicalUsageTest
{
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void precondition()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] args = new String[0];
			validators.requireThat(args, "args").length().isPositive();
		}
	}

	@Test(expectedExceptions = AssertionError.class)
	public void invariantOrPostCondition()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] args = {"planet"};
			// Precondition
			validators.requireThat(args, "args").length().isPositive();

			// Class invariant or method postcondition
			assert validators.that(args[0], "args[0]").isEqualTo("world").elseThrow();
		}
	}

	@Test(expectedExceptions = MultipleFailuresException.class)
	public void multipleFailures()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] args = {"world"};
			// Precondition
			validators.requireThat(args, "args").length().isPositive();

			// Class invariant or method postcondition
			assert validators.that(args[0], "args[0]").isEqualTo("world").elseThrow();

			// Throwing multiple validation failures at once
			ValidationFailures failures = validators.checkIf(args, "args").isEmpty().elseGetFailures();
			failures.addAll(validators.checkIf(args[0], "args[0]").isEqualTo("planet").elseGetFailures());
			failures.throwOnFailure();
		}
	}

	@Test
	public void context()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Duration duration = Duration.ofDays(1);
			Set<Duration> bucket = Set.of();
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
			TestValidators validators = TestValidators.of(scope);

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
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int actual = 5;
			validators.that(actual).isEqualTo(6).elseThrow();
		}
	}
}