/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleIntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

/**
 * Default implementation of {@code ExtensibleIntegerVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value
 */
public abstract class AbstractIntegerVerifier<S, T extends Number & Comparable<? super T>>
	extends AbstractNumberVerifier<S, T>
	implements ExtensibleIntegerVerifier<S, T>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	protected AbstractIntegerVerifier(ApplicationScope scope, String name, T actual,
	                                  Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public S isWholeNumber()
	{
		return getThis();
	}

	@Override
	public S isNotWholeNumber()
	{
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " may not be a whole number.").
			addContext("Actual", actual).
			build();
	}
}
