/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleComparableVerifier;

/**
 * A {@code ExtensibleComparableVerifier} that does nothing.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value
 */
public abstract class AbstractComparableVerifierNoOp<S, T extends Comparable<? super T>>
	extends AbstractObjectVerifierNoOp<S, T>
	implements ExtensibleComparableVerifier<S, T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public AbstractComparableVerifierNoOp(Configuration config)
	{
		super(config);
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
