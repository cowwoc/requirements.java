/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.math.BigDecimal;

/**
 * Verifies preconditions of a {@link BigDecimal} parameter.
 * <p>
 * @author Gili Tzabari
 */
public final class BigDecimalPreconditions
	extends NumberPreconditions<BigDecimalPreconditions, BigDecimal>
{
	/**
	 * Creates new BigDecimalPreconditions.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	BigDecimalPreconditions(BigDecimal parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
	}

	/**
	 * Ensures that the parameter's precision is within range.
	 * <p>
	 * @param range the range
	 * @return this
	 * @throws NullPointerException     if range is null
	 * @throws IllegalArgumentException if the precision is not in range
	 */
	public BigDecimalPreconditions hasPrecisionIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(range, "range").isNotNull();
		boolean inRange = range.contains(parameter.precision());
		if (inRange)
			return this;
		StringBuilder message = new StringBuilder(name + ".precision() (" + parameter.precision() +
			") must be in the range ");
		switch (range.lowerBoundType())
		{
			case OPEN:
				message.append("(");
				break;
			case CLOSED:
				message.append("[");
				break;
			default:
				throw new AssertionError(range.lowerBoundType().name());
		}
		message.append(range.lowerEndpoint()).append(", ").append(range.upperEndpoint());
		switch (range.lowerBoundType())
		{
			case OPEN:
				message.append(")");
				break;
			case CLOSED:
				message.append("]");
				break;
			default:
				throw new AssertionError(range.lowerBoundType().name());
		}
		throw new IllegalArgumentException(message.toString());
	}

	/**
	 * Ensures that the parameter's precision is within range.
	 * <p>
	 * @param range the range
	 * @return this
	 * @throws NullPointerException     if range is null
	 * @throws IllegalArgumentException if the precision is not in range
	 */
	public BigDecimalPreconditions hasScaleIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(range, "range").isNotNull();
		boolean inRange = range.contains(parameter.scale());
		if (inRange)
			return this;
		StringBuilder message = new StringBuilder(name + ".scale() (" + parameter.scale() + ") must " +
			"be in the range ");
		switch (range.lowerBoundType())
		{
			case OPEN:
				message.append("(");
				break;
			case CLOSED:
				message.append("[");
				break;
			default:
				throw new AssertionError(range.lowerBoundType().name());
		}
		message.append(range.lowerEndpoint()).append(", ").append(range.upperEndpoint());
		switch (range.lowerBoundType())
		{
			case OPEN:
				message.append(")");
				break;
			case CLOSED:
				message.append("]");
				break;
			default:
				throw new AssertionError(range.lowerBoundType().name());
		}
		throw new IllegalArgumentException(message.toString());
	}
}
