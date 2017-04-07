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
import org.bitbucket.cowwoc.requirements.core.Verifiers;
import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import static org.bitbucket.cowwoc.requirements.guava.Requirements.assertThat;
import static org.bitbucket.cowwoc.requirements.guava.Requirements.requireThat;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
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

			Verifiers verifier = new Verifiers(scope);
			verifier.requireThat(duration, "duration").isGreaterThan(Duration.ofDays(0));
			try
			{
				verifier.addContext("MyKey", "SomeContext").
					requireThat(bucket, "bucket").contains(duration);
			}
			catch (IllegalArgumentException e)
			{
				if (!e.getMessage().contains("MyKey") || !e.getMessage().contains("SomeContext"))
					throw new AssertionError(e);
			}
		}
	}
}
