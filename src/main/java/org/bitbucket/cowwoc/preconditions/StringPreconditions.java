/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * Verifies preconditions of a String parameter.
 * <p>
 * @author Gili Tzabari
 */
public final class StringPreconditions extends Preconditions<StringPreconditions, String>
{
	private final static Pattern emailPattern = Pattern.compile("[^@]+@[^@]+");

	/**
	 * Creates new StringPreconditions.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	StringPreconditions(String parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
	}

	/**
	 * Ensures that the parameter is not empty.
	 * <p>
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
	 * <p>
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
			parameter.length() + ". " + name + ": \"" + parameter + "\"");
	}

	/**
	 * Ensures that the parameter isn't too short.
	 * <p>
	 * @param minLength the minimum length allowed for the string
	 * @return this
	 * @throws IllegalArgumentException if parameter is too short
	 */
	public StringPreconditions hasMinimumLength(int minLength) throws IllegalArgumentException
	{
		if (parameter.length() < minLength)
		{
			throw new IllegalArgumentException(name + " may not be shorter than " + minLength +
				" characters, was: " + parameter.length() + ". " + name + ": \"" + parameter + "\"");
		}
		return this;
	}

	/**
	 * Ensures that the parameter's length is equal to a single value.
	 * <p>
	 * @param length the expected length of the string
	 * @return this
	 * @throws IllegalArgumentException if parameter length is incorrect
	 */
	public StringPreconditions hasLength(int length) throws IllegalArgumentException
	{
		if (parameter.length() != length)
		{
			throw new IllegalArgumentException(name + "'s length must be equal to " + length +
				" characters, was " + parameter.length() + ". " + name + ": \"" + parameter + "\"");
		}
		return this;
	}

	/**
	 * Ensures that the parameter isn't too long.
	 * <p>
	 * @param maxLength the maximum length allowed for the string
	 * @return this
	 * @throws IllegalArgumentException if parameter is too long
	 */
	public StringPreconditions hasMaximumLength(int maxLength) throws IllegalArgumentException
	{
		if (parameter.length() > maxLength)
		{
			throw new IllegalArgumentException(name + " may not be longer than " + maxLength +
				" characters, was: " + parameter.length() + ". " + name + ": \"" + parameter + "\"");
		}
		return this;
	}

	/**
	 * Ensures that the parameter contains a valid email format.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not contain a valid email format
	 */
	public StringPreconditions isEmailFormat() throws IllegalArgumentException
	{
		if (!emailPattern.matcher(parameter).matches())
		{
			throw new IllegalArgumentException(name + " does not contain a valid email format: " +
				parameter);
		}
		return this;
	}

	/**
	 * Ensures that the parameter contains a valid IP address format. This check implies
	 * {@code isNotNull()} and {@code isNotEmpty()}.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not contain a valid IP address format
	 */
	public StringPreconditions isIpAddressFormat() throws IllegalArgumentException
	{
		isNotNull().isNotEmpty();
		char firstCharacter = parameter.charAt(0);
		if (Character.digit(firstCharacter, 16) == -1 && (firstCharacter != ':'))
		{
			throw new IllegalArgumentException(name + " does not contain a valid IP address format: " +
				parameter);
		}
		try
		{
			InetAddress.getByName(parameter);
		}
		catch (UnknownHostException e)
		{
			throw new IllegalArgumentException(name + " does not contain a valid IP address format: " +
				parameter, e);
		}
		return this;
	}

	/**
	 * Ensures that the parameter starts with a value.
	 * <p>
	 * @param prefix the value the string must start with
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not start with prefix
	 */
	public StringPreconditions startsWith(String prefix) throws IllegalArgumentException
	{
		if (!parameter.startsWith(prefix))
			throw new IllegalArgumentException(name + " must start with: " + prefix);
		return this;
	}

	/**
	 * Ensures that the parameter does not start with a value.
	 * <p>
	 * @param prefix the value the string must not start with
	 * @return this
	 * @throws IllegalArgumentException if the parameter starts with prefix
	 */
	public StringPreconditions doesNotStartWith(String prefix) throws IllegalArgumentException
	{
		if (parameter.startsWith(prefix))
			throw new IllegalArgumentException(name + " must not start with: " + prefix);
		return this;
	}

	/**
	 * Ensures that the parameter ends with a value.
	 * <p>
	 * @param suffix the value the string must end with
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not end with suffix
	 */
	public StringPreconditions endsWith(String suffix) throws IllegalArgumentException
	{
		if (!parameter.endsWith(suffix))
			throw new IllegalArgumentException(name + " must end with: " + suffix);
		return this;
	}

	/**
	 * Ensures that the parameter does not end with a value.
	 * <p>
	 * @param suffix the value the string must not end with
	 * @return this
	 * @throws IllegalArgumentException if the parameter ends with suffix
	 */
	public StringPreconditions doesNotEndWith(String suffix) throws IllegalArgumentException
	{
		if (parameter.endsWith(suffix))
			throw new IllegalArgumentException(name + " must not end with: " + suffix);
		return this;
	}
}
