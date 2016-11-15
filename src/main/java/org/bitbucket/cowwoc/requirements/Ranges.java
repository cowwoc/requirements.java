/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

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
	 * @param name  the name of the variable whose value is out of range
	 * @param value the value that is out of range
	 * @param <C>   the type of value
	 * @param range the expected range
	 * @return an exception message indicating that the value is out of range
	 * @throws AssertionError if {@code name}, {@code value} or {@code range} are null
	 */
	public static <C extends Comparable<? super C>> String getExceptionMessage(String name, C value,
		Range<C> range) throws AssertionError
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (value != null): "value may not be null";
		assert (range != null): "range may not be null";
		StringBuilder message = new StringBuilder(name + " must be in the range ");
		appendRange(range, message);
		message.append("\n").
			append(String.format("Actual: %s", value));
		return message.toString();
	}

	/**
	 * @param name  the name of the variable whose value is out of range
	 * @param value the value that is out of range
	 * @param <T>   the type of value
	 * @param range the expected range
	 * @return an exception message indicating that the value is out of range
	 * @throws AssertionError if {@code name}, {@code value} or {@code range} are null
	 */
	public static <T extends Number & Comparable<? super T>> String getExceptionMessage(String name,
		T value, Range<T> range) throws AssertionError
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (value != null): "value may not be null";
		assert (range != null): "range may not be null";
		StringBuilder message = new StringBuilder(name + " must be in the range ");
		appendRange(range, message);
		message.append("\n").
			append(String.format("Actual: %,d", value.longValue()));
		return message.toString();
	}

	/**
	 * @param range   the expected range
	 * @param <C>     the type of the range
	 * @param message the message to append to
	 * @return an exception message indicating that the value is out of range
	 * @throws AssertionError if {@code range} is null
	 */
	public static <C extends Comparable<? super C>> String toString(Range<C> range)
		throws AssertionError
	{
		return appendRange(range, new StringBuilder(16));
	}

	/**
	 * @param range   the expected range
	 * @param <C>     the type of the range
	 * @param message the message to append to
	 * @return {@code message.toString()}
	 * @throws AssertionError if {@code range} or {@code message} are null
	 */
	public static <C extends Comparable<? super C>> String appendRange(Range<C> range,
		StringBuilder message) throws AssertionError
	{
		assert (range != null): "range may not be null";
		assert (message != null): "message may not be null";
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
