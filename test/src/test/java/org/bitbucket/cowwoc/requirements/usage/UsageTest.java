/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.usage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import static org.bitbucket.cowwoc.requirements.core.Requirements.requireThat;
import org.bitbucket.cowwoc.requirements.core.UnifiedVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestSingletonScope;
import static org.bitbucket.cowwoc.requirements.guava.Requirements.assertThat;
import static org.bitbucket.cowwoc.requirements.guava.Requirements.requireThat;
import org.testng.annotations.Test;

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

		// Java will invoke core.Requirements.requireThat() or guava.Requirements.requireThat()
		// depending on the context
		requireThat(map, "map").size().isPositive();
		requireThat(multimap, "multimap").entrySet().containsAll(ImmutableList.of(
			Maps.immutableEntry(1, 5), Maps.immutableEntry(1, 6)));

		// Assertions work too
		assertThat(multimap, "multimap").size().isPositive();
	}

	@Test
	public void withContext()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Duration duration = Duration.ofDays(1);
			Set<Duration> bucket = Collections.emptySet();

			UnifiedVerifier verifier = new UnifiedVerifier(scope, true);
			verifier.requireThat(duration, "duration").isGreaterThan(Duration.ofDays(0));
			try
			{
				verifier.requireThat(bucket, "bucket").addContext("MyKey", "SomeContext").
					contains(duration);
			}
			catch (IllegalArgumentException e)
			{
				if (!e.getMessage().contains("MyKey") || !e.getMessage().contains("SomeContext"))
					throw new AssertionError(e);
			}
		}
	}
}
