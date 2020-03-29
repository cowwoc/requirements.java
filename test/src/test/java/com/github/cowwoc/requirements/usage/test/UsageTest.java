/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.usage.test;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static com.github.cowwoc.requirements.DefaultRequirements.assertThat;
import static com.github.cowwoc.requirements.DefaultRequirements.requireThat;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

public final class UsageTest
{
	/**
	 * Demonstrate the ability to statically import the same method name from different modules.
	 */
	@Test
	public void verifiersFromMultipleModules()
	{
		Map<Integer, Integer> map = Collections.singletonMap(1, 5);
		Multimap<Integer, Integer> multimap = ImmutableMultimap.of(1, 5, 1, 6);

		// Java will invoke java.Requirements.requireThat() or guava.Requirements.requireThat()
		// depending on the context
		requireThat(map, "map").size().isPositive();
		requireThat(multimap, "multimap").entries().containsAll(ImmutableList.of(
			Maps.immutableEntry(1, 5), Maps.immutableEntry(1, 6)));

		// Assertions work too
		assertThat(multimap, "multimap").size().isPositive();
	}

	@Test
	public void withContext()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Duration duration = Duration.ofDays(1);
			Set<Duration> bucket = Collections.emptySet();

			Requirements verifier = new Requirements(scope);
			verifier.requireThat(duration, "duration").isGreaterThan(Duration.ofDays(0));
			try
			{
				verifier.putContext("SomeName", "SomeContext").
					requireThat(bucket, "bucket").contains(duration);
			}
			catch (IllegalArgumentException e)
			{
				if (!e.getMessage().contains("SomeName") || !e.getMessage().contains("SomeContext"))
					throw new AssertionError(e);
			}
		}
	}
}
