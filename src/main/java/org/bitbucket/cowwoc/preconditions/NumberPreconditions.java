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
public abstract class NumberPreconditions<S extends NumberPreconditions<S, T>, T extends Comparable<? super T>>
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
		throws IllegalArgumentException
	{
		boolean inRange = range.contains(parameter);
		if (inRange)
			return self;
		Range<T> canonical = range.canonical(domain);
		throw new IllegalArgumentException(name + " must be in the range[" + canonical.lowerEndpoint() +
			", " + canonical.upperEndpoint() + "): " + parameter);
	}

	/**
	 * Ensures that the parameter is not negative.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is negative
	 */
	public abstract S isNotNegative();

	/**
	 * Ensures that the parameter is positive.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not positive
	 */
	public abstract S isPositive();
}
