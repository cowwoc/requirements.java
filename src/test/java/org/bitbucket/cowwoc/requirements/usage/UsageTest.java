/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.usage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.time.Duration;
import java.util.Set;
import org.bitbucket.cowwoc.requirements.Assertions;
import static org.bitbucket.cowwoc.requirements.Requirements.assertThat;
import static org.bitbucket.cowwoc.requirements.Requirements.requireThat;
import static org.bitbucket.cowwoc.requirements.usage.DurationRequirements.requireThat;
import org.testng.annotations.Test;

public final class UsageTest
{
	/**
	 * Demonstrate the ability to statically import the same method name from different modules.
	 */
	@Test
	public void requirementsFromMultipleModules()
	{
		Duration duration = Duration.ofDays(1);
		Set<Duration> bucket = ImmutableSet.of(duration);

		// Java will invoke Requirements.requireThat() or DurationRequirements.requireThat() depending on the context
		requireThat(duration, "duration").isGreaterThan(Duration.ofDays(0));
		requireThat(bucket, "bucket").contains(duration);
	}

	@Test
	public void globalAsserts()
	{
		Duration duration = Duration.ofDays(1);
		Set<Duration> bucket = ImmutableSet.of(duration);

		assertThat(duration, "duration").isGreaterThan(Duration.ofDays(0));
		assertThat(bucket, "bucket").contains(duration);
	}

	@Test
	@SuppressWarnings(
		{
			"AssertWithSideEffects", "NestedAssignment"
		})
	public void localAsserts()
	{
		boolean assertionsEnabled = false;
		assert (assertionsEnabled = true);
		Assertions assertions = new Assertions(assertionsEnabled);
		Duration duration = Duration.ofDays(1);
		Set<Duration> bucket = ImmutableSet.of(duration);

		assertions.requireThat(duration, "duration").isGreaterThan(Duration.ofDays(0));
		assertions.requireThat(bucket, "bucket").contains(duration);
	}

	@Test
	public void withContext()
	{
		Duration duration = Duration.ofDays(1);
		Set<Duration> bucket = ImmutableSet.of();

		requireThat(duration, "duration").isGreaterThan(Duration.ofDays(0));
		try
		{
			requireThat(bucket, "bucket").withContext(ImmutableMap.of("MyKey", "SomeContext")).
				contains(duration);
		}
		catch (IllegalArgumentException e)
		{
			if (!e.getMessage().contains("MyKey") || !e.getMessage().contains("SomeContext"))
				throw new AssertionError(e);
		}
	}
}
