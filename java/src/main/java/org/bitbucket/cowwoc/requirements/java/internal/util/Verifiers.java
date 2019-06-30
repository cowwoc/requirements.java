/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.util;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Verifier helper functions.
 */
public final class Verifiers
{
	/**
	 * Prevent construction.
	 */
	private Verifiers()
	{
	}

	/**
	 * @param scope         the application configuration
	 * @param configuration a verifier's configuration
	 * @param name          the name of the actual value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static void verifyName(ApplicationScope scope, Configuration configuration, String name)
	{
		if (name == null)
		{
			throw new ExceptionBuilder<>(scope, configuration, NullPointerException.class,
				"name may not be null").
				build();
		}
		if (name.trim().isEmpty())
		{
			throw new ExceptionBuilder<>(scope, configuration, IllegalArgumentException.class,
				"name may not be empty").
				build();
		}
	}
}
