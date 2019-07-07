/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ComparableValidator;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

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
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public ComparableValidatorImpl(ApplicationScope scope, Configuration config, String name, T actual,
	                               List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected ComparableValidator<T> getThis()
	{
		return this;
	}

	@Override
	protected ComparableValidator<T> getNoOp()
	{
		return new ComparableValidatorNoOp<>(failures);
	}
}
