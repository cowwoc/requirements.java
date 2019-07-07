/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ComparableValidator;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * An implementation of {@code ComparableValidator} that does nothing.
 *
 * @param <T> the type of the value being validated
 */
public final class ComparableValidatorNoOp<T extends Comparable<? super T>>
	extends AbstractComparableValidatorNoOp<ComparableValidator<T>, T>
	implements ComparableValidator<T>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public ComparableValidatorNoOp(ApplicationScope scope, Configuration config,
	                               List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	protected ComparableValidator<T> getThis()
	{
		return this;
	}
}
