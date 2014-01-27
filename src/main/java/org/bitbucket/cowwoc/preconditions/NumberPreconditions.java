/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

/**
 * Verifies preconditions of a Number parameter.
 * <p/>
 * @param <S> the type of preconditions that was instantiated
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public abstract class NumberPreconditions<S extends NumberPreconditions<S, T>, T extends Number & Comparable<? super T>>
	extends Preconditions<S, T>
{
	/**
	 * Creates new NumberPreconditions.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	NumberPreconditions(T parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
	}

	/**
	 * Ensures that the parameter is within range.
	 * <p/>
	 * @param range  the range
	 * @param domain the number domain
	 * @return this
	 * @throws NullPointerException     if domain is null
	 * @throws IllegalArgumentException if the parameter is not in range
	 */
	protected S isIn(Range<T> range, DiscreteDomain<T> domain)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(domain, "domain").isNotNull();
		boolean inRange = range.contains(parameter);
		if (inRange)
			return self;
		Range<T> canonical = range.canonical(domain);
		throw new IllegalArgumentException(name + " (" + parameter + ") must be in the range [" +
			canonical.lowerEndpoint() + ", " + canonical.upperEndpoint() + "]");
	}

	/**
	 * Ensures that the parameter is not negative.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is negative
	 */
	public abstract S isNotNegative() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is positive.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not positive
	 */
	public abstract S isPositive() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is greater than a value.
	 * <p>
	 * @param value the value the parameter must be greater than
	 * @param name  the name of the value
	 * @return this
	 * @throws IllegalArgumentException if the parameter is less than or equal to value
	 */
	public S isGreaterThan(long value, String name) throws IllegalArgumentException
	{
		if (parameter.longValue() <= value)
		{
			throw new IllegalArgumentException(this.name + " (" + parameter + ") must be greater than " +
				name + " (" + value + ")");
		}
		return self;
	}
}
