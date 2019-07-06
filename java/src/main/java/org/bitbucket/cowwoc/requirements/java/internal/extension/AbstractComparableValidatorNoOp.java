/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleComparableValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * A {@code ExtensibleComparableValidator} that does nothing.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value
 */
public abstract class AbstractComparableValidatorNoOp<S, T extends Comparable<? super T>>
	extends AbstractObjectValidatorNoOp<S, T>
	implements ExtensibleComparableValidator<S, T>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public AbstractComparableValidatorNoOp(ApplicationScope scope, Configuration config,
	                                       List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	public S isGreaterThan(T value)
	{
		return getThis();
	}

	@Override
	public S isGreaterThan(T value, String name)
	{
		return getThis();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value)
	{
		return getThis();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value, String name)
	{
		return getThis();
	}

	@Override
	public S isLessThan(T value)
	{
		return getThis();
	}

	@Override
	public S isLessThan(T value, String name)
	{
		return getThis();
	}

	@Override
	public S isLessThanOrEqualTo(T value)
	{
		return getThis();
	}

	@Override
	public S isLessThanOrEqualTo(T value, String name)
	{
		return getThis();
	}

	@Override
	public S isComparableTo(T expected)
	{
		return getThis();
	}

	@Override
	public S isComparableTo(T expected, String name)
	{
		return getThis();
	}

	@Override
	public S isNotComparableTo(T other)
	{
		return getThis();
	}

	@Override
	public S isNotComparableTo(T other, String name)
	{
		return getThis();
	}

	@Override
	public S isBetween(T startInclusive, T endExclusive)
	{
		return getThis();
	}

	@Override
	public S isBetweenClosed(T startInclusive, T endInclusive)
	{
		return getThis();
	}
}
