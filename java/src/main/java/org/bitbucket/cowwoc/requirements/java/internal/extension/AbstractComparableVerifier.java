/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleComparableValidator;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleComparableVerifier;

/**
 * Extensible implementation of {@code ExtensibleComparableVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <V> the type of validator used by the verifier
 * @param <T> the type of the value being verified
 */
public abstract class AbstractComparableVerifier<S, V extends ExtensibleComparableValidator<V, T>,
	T extends Comparable<? super T>>
	extends AbstractObjectVerifier<S, V, T>
	implements ExtensibleComparableVerifier<S, T>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	protected AbstractComparableVerifier(V validator)
	{
		super(validator);
	}

	@Override
	public S isLessThan(T value)
	{
		validator = validator.isLessThan(value);
		return validationResult();
	}

	@Override
	public S isLessThan(T value, String name)
	{
		validator = validator.isLessThan(value, name);
		return validationResult();
	}

	@Override
	public S isLessThanOrEqualTo(T value)
	{
		validator = validator.isLessThanOrEqualTo(value);
		return validationResult();
	}

	@Override
	public S isLessThanOrEqualTo(T value, String name)
	{
		validator = validator.isLessThanOrEqualTo(value, name);
		return validationResult();
	}

	@Override
	public S isGreaterThan(T value)
	{
		validator = validator.isGreaterThan(value);
		return validationResult();
	}

	@Override
	public S isGreaterThan(T value, String name)
	{
		validator = validator.isGreaterThan(value, name);
		return validationResult();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value)
	{
		validator = validator.isGreaterThanOrEqualTo(value);
		return validationResult();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value, String name)
	{
		validator = validator.isGreaterThanOrEqualTo(value, name);
		return validationResult();
	}

	@Override
	public S isComparableTo(T expected)
	{
		validator = validator.isComparableTo(expected);
		return validationResult();
	}

	@Override
	public S isComparableTo(T expected, String name)
	{
		validator = validator.isComparableTo(expected, name);
		return validationResult();
	}

	@Override
	public S isNotComparableTo(T value)
	{
		validator = validator.isNotComparableTo(value);
		return validationResult();
	}

	@Override
	public S isNotComparableTo(T other, String name)
	{
		validator = validator.isNotComparableTo(other, name);
		return validationResult();
	}

	@Override
	public S isBetween(T startInclusive, T endExclusive)
	{
		validator = validator.isBetween(startInclusive, endExclusive);
		return validationResult();
	}

	@Override
	public S isBetweenClosed(T startInclusive, T endInclusive)
	{
		validator = validator.isBetweenClosed(startInclusive, endInclusive);
		return validationResult();
	}
}
