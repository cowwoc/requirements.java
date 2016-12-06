/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.usage;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;
import org.bitbucket.cowwoc.requirements.UnifiedVerifier;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.scope.TestSingletonScope;
import static org.bitbucket.cowwoc.requirements.usage.TimeRequirements.assertThat;
import static org.bitbucket.cowwoc.requirements.usage.TimeRequirements.requireThat;
import org.testng.annotations.Test;

public final class UsageTest
{
	/**
	 * Demonstrate the ability to statically import the same method name from different modules.
	 */
	@Test
	public void requirementsFromMultipleModules()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			UnifiedVerifier verifier = new UnifiedVerifier(scope, true);
			Duration duration = Duration.ofDays(1);
			Set<Duration> bucket = Collections.singleton(duration);

			// Java will invoke Requirements.requireThat() or TimeRequirements.requireThat() depending on the context
			requireThat(duration, "duration").isPositive();
			verifier.requireThat(bucket, "bucket").contains(duration);

			// Assertions work too
			assertThat(duration, "duration").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void localRequirements_Failure()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			UnifiedVerifier verifier = new UnifiedVerifier(scope, true).
				addContext("key", "value").withException(IllegalStateException.class);
			Duration duration = Duration.ofDays(1);

			verifier.requireThat(duration, "duration").isNull();
		}
	}

	@Test
	public void assertsEnabled()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			UnifiedVerifier verifier = new UnifiedVerifier(scope, true).
				addContext("key", "value").withException(IllegalStateException.class);
			Duration duration = Duration.ofDays(1);
			Set<Duration> bucket = Collections.singleton(duration);

			verifier.requireThat(duration, "duration").isGreaterThan(Duration.ofDays(0));
			verifier.requireThat(bucket, "bucket").contains(duration);
		}
	}

	@Test
	public void assertsDisabled()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			UnifiedVerifier verifier = new UnifiedVerifier(scope, true).
				addContext("key", "value").withException(IllegalStateException.class);
			Duration duration = Duration.ofDays(1);
			Set<Duration> bucket = Collections.singleton(duration);

			verifier.requireThat(duration, "duration").isGreaterThan(Duration.ofDays(0));
			verifier.requireThat(bucket, "bucket").contains(duration);
		}
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void assertsEnabled_Failure()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			UnifiedVerifier verifier = new UnifiedVerifier(scope, true).
				addContext("key", "value").withException(IllegalStateException.class);
			Duration duration = Duration.ofDays(1);

			verifier.requireThat(duration, "duration").isNull();
		}
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
