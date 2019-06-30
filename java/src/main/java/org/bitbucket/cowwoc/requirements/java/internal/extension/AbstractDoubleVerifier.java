/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

/**
 * An implementation of {@code ExtensibleFloatingPointVerifier} for {@code double}s.
 *
 * @param <S> the type of verifier returned by the methods
 */
public abstract class AbstractDoubleVerifier<S>
	extends AbstractNumberVerifier<S, Double>
	implements ExtensibleFloatingPointVerifier<S, Double>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	protected AbstractDoubleVerifier(ApplicationScope scope, String name, Double actual,
	                                 Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public S isNumber()
	{
		if (!actual.isNaN())
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be a number.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotNumber()
	{
		if (actual.isNaN())
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " may not be a number.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isFinite()
	{
		if (!actual.isInfinite())
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be finite.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotFinite()
	{
		if (actual.isInfinite())
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be infinite.").
			addContext("Actual", actual).
			build();
	}
}
