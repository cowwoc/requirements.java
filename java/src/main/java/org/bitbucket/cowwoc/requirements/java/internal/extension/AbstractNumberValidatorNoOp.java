/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleNumberValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * An {@code ExtensibleNumberValidator} that does nothing. A validator that ignores all subsequent
 * failures because they are guaranteed to fail and would add any value to the end-user. For example, an
 * attempt was made to dereference null or cast the value to an incompatible type.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value being validated
 */
public abstract class AbstractNumberValidatorNoOp<S, T extends Number & Comparable<? super T>>
	extends AbstractComparableValidatorNoOp<S, T>
	implements ExtensibleNumberValidator<S, T>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public AbstractNumberValidatorNoOp(ApplicationScope scope, Configuration config,
	                                   List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	public S isNegative()
	{
		return getThis();
	}

	@Override
	public S isNotNegative()
	{
		return getThis();
	}

	@Override
	public S isZero()
	{
		return getThis();
	}

	@Override
	public S isNotZero()
	{
		return getThis();
	}

	@Override
	public S isPositive()
	{
		return getThis();
	}

	@Override
	public S isNotPositive()
	{
		return getThis();
	}

	@Override
	public S isWholeNumber()
	{
		return getThis();
	}

	@Override
	public S isNotWholeNumber()
	{
		return getThis();
	}

	@Override
	public S isMultipleOf(T divisor)
	{
		return getThis();
	}

	@Override
	public S isMultipleOf(T divisor, String name)
	{
		return getThis();
	}

	@Override
	public S isNotMultipleOf(T divisor)
	{
		return getThis();
	}

	@Override
	public S isNotMultipleOf(T divisor, String name)
	{
		return getThis();
	}
}
