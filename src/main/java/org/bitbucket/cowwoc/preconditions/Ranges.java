/*
 * Copyright 2015 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import static com.google.common.collect.BoundType.CLOSED;
import static com.google.common.collect.BoundType.OPEN;
import com.google.common.collect.Range;

/**
 * Range helper functions.
 * <p>
 * @author Gili Tzabari
 */
final class Ranges
{
	/**
	 * @param <C>   the type of value that is out of range
	 * @param name  the name of the variable whose value is out of range
	 * @param value the value that is out of range
	 * @param range the expected range
	 * @return an exception message indicating that the value is out of range
	 * @throws AssertionError if name, value or range are null
	 */
	public static <C extends Comparable<? super C>> String getExceptionMessage(String name, C value,
		Range<C> range)
	{
		assert (name != null);
		assert (value != null);
		assert (range != null);
		StringBuilder message = new StringBuilder(name + " (" + String.valueOf(value) +
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
		switch (range.upperBoundType())
		{
			case OPEN:
				message.append(")");
				break;
			case CLOSED:
				message.append("]");
				break;
			default:
				throw new AssertionError(range.upperBoundType().name());
		}
		return message.toString();
	}

	/**
	 * Prevent construction.
	 */
	private Ranges()
	{
	}
}
