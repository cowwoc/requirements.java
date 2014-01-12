/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import java.util.regex.Pattern;

/**
 * Verifies preconditions of a String parameter.
 * <p/>
 * @author Gili Tzabari
 */
public final class StringPreconditions extends Preconditions<StringPreconditions, String>
{
	private final static Pattern emailPattern = Pattern.compile("[^@]+@[^@]+");

	/**
	 * Creates new StringPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	StringPreconditions(String name, String parameter)
	{
		super(name, parameter);
	}

	/**
	 * Ensures that the parameter is not empty.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is empty
	 */
	public StringPreconditions isNotEmpty() throws IllegalArgumentException
	{
		if (parameter.trim().isEmpty())
			throw new IllegalArgumentException(name + " may not be empty");
		return this;
	}

	/**
	 * Ensures that the parameter length is within a range.
	 * <p/>
	 * @param range the range of acceptable parameter lengths
	 * @return this
	 * @throws IllegalArgumentException if parameter's length is outside of the specified range
	 * @throws NullPointerException     if parameter or range are null
	 */
	public StringPreconditions lengthIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		if (parameter == null)
			throw new NullPointerException("parameter may not be null");
		if (range == null)
			throw new NullPointerException("range may not be null");
		boolean inRange = range.contains(parameter.length());
		if (inRange)
			return this;
		Range<Integer> canonical = range.canonical(DiscreteDomain.integers());
		throw new IllegalArgumentException(name + " must have a length in the " +
			"range[" + canonical.lowerEndpoint() + ", " + (canonical.upperEndpoint() - 1) + "], was " +
			parameter.length() + ". Parameter: " + parameter);
	}

	/**
	 * Ensures that the parameter isn't too long.
	 * <p/>
	 * @param maxLength the maximum length allowed for the string
	 * @return this
	 * @throws IllegalArgumentException if parameter is too long
	 */
	public StringPreconditions isShorterThan(int maxLength) throws IllegalArgumentException
	{
		if (parameter.length() >= maxLength)
		{
			throw new IllegalArgumentException(name + " may not be longer than " + maxLength +
				" characters. Was: " + parameter.length());
		}
		return this;
	}

	/**
	 * Ensures that a String is a valid email address.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if email is not a valid email address
	 */
	public StringPreconditions isValidEmail() throws IllegalArgumentException
	{
		if (!emailPattern.matcher(parameter).matches())
			throw new IllegalArgumentException(name + " is not a valid email address: " + parameter);
		return this;
	}
}
