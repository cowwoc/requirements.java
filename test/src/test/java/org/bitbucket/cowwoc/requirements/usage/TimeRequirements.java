/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.usage;

import java.time.Duration;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.scope.TestSingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Verifies requirements of a time parameter.
 * <p>
 * All verifier implementations must be immutable and final.
 *
 * @author Gili Tzabari
 */
@SuppressWarnings(
	{
		"NestedAssignment", "AssertWithSideEffects"
	})
public final class TimeRequirements
{
	private static final boolean ASSERTIONS_ENABLED;

	static
	{
		boolean temp = false;
		assert (temp = true);
		ASSERTIONS_ENABLED = temp;
	}

	/**
	 * Creates requirements for an {@code Object}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static DurationRequirements requireThat(Duration actual, String name)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (name.trim().isEmpty())
			throw new IllegalArgumentException("name may not be empty");
		SingletonScope scope = new TestSingletonScope();
		return new DurationRequirementsImpl(scope, actual, name, Configuration.initial());
	}

	/**
	 * Same as {@link #requireThat(Duration, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static DurationRequirements assertThat(Duration actual, String name)
	{
		if (ASSERTIONS_ENABLED)
			return requireThat(actual, name);
		return NoOpDurationRequirements.INSTANCE;
	}

	/**
	 * Prevents construction.
	 */
	private TimeRequirements()
	{
	}
}
