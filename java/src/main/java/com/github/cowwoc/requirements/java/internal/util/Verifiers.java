/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.util;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.ValidationFailureImpl;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

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
	 * @throws NullPointerException     if any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	public static void verifyName(ApplicationScope scope, Configuration configuration, String name)
	{
		if (name == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, configuration, NullPointerException.class,
				"name may not be null");
			throw failure.createException(NullPointerException.class);
		}
		if (name.isBlank())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, configuration,
				IllegalArgumentException.class, "name may not be empty");
			throw failure.createException(IllegalArgumentException.class);
		}
	}
}
