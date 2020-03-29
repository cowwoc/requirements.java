/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ComparableValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.internal.extension.AbstractComparableValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@code ComparableValidator}.
 *
 * @param <T> the type of objects that the value may be compared to
 */
public final class ComparableValidatorImpl<T extends Comparable<? super T>>
	extends AbstractComparableValidator<ComparableValidator<T>, T>
	implements ComparableValidator<T>
{
	/**
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public ComparableValidatorImpl(ApplicationScope scope, Configuration config, String name, T actual)
	{
		super(scope, config, name, actual, NO_FAILURES);
	}

	@Override
	protected ComparableValidator<T> getThis()
	{
		return this;
	}

	@Override
	protected ComparableValidator<T> getNoOp()
	{
		return new ComparableValidatorNoOp<>(getFailures());
	}
}
