/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

/**
 * Verifies preconditions of a Integer parameter.
 * <p/>
 * @author Gili Tzabari
 */
public final class IntegerPreconditions extends NumberPreconditions<IntegerPreconditions, Integer>
{
	/**
	 * Creates new IntegerPreconditions.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	IntegerPreconditions(Integer parameter, String name)
	{
		super(parameter, name);
	}

	/**
	 * Ensures that the parameter is within range.
	 * <p/>
	 * @param range the range
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not in range
	 */
	public IntegerPreconditions isIn(Range<Integer> range) throws IllegalArgumentException
	{
		return isIn(range, DiscreteDomain.integers());
	}

	@Override
	public IntegerPreconditions isNotNegative()
	{
		if (parameter.compareTo(0) < 0)
			throw new IllegalArgumentException(name + " may not be negative");
		return this;
	}

	@Override
	public IntegerPreconditions isPositive()
	{
		if (parameter.compareTo(0) <= 0)
			throw new IllegalArgumentException(name + " may not be negative");
		return this;
	}
}
