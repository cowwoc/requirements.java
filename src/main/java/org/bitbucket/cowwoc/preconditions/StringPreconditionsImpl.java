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
 * Default implementation of StringPreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class StringPreconditionsImpl extends ObjectPreconditionsImpl<StringPreconditions, String>
	implements StringPreconditions
{
	private final static Pattern emailPattern = Pattern.compile("[^@]+@[^@]+");

	/**
	 * Creates new StringPreconditionsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	StringPreconditionsImpl(String parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
	}

	/**
	 * Ensures that the parameter is empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not empty
	 * @see #trim()
	 */
	@Override
	public StringPreconditions isEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s must be empty", name));
	}

	/**
	 * Ensures that the parameter is not empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is empty
	 * @see #trim()
	 */
	@Override
	public StringPreconditions isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s may not be empty", name));
	}

	/**
	 * Trims whitespace at the beginning and end of the parameter.
	 * <p>
	 * @return this
	 */
	@Override
	public StringPreconditions trim()
	{
		this.parameter = parameter.trim();
		return this;
	}

	/**
	 * Ensures that the parameter length is within a range.
	 * <p>
	 * @param range the range of acceptable parameter lengths
	 * @return this
	 * @throws IllegalArgumentException if parameter's length is outside of the specified range
	 * @throws NullPointerException     if range is null
	 */
	@Override
	public StringPreconditions lengthIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(range, "range").isNotNull();
		if (range.contains(parameter.length()))
			return this;
		Range<Integer> canonical = range.canonical(DiscreteDomain.integers());
		return throwException(IllegalArgumentException.class,
			String.format("%s's length must be in the range [%d, %d]. Length was %d. Value was \"%s\".",
				name, canonical.lowerEndpoint(), (canonical.upperEndpoint() - 1), parameter.length(),
				parameter));
	}

	/**
	 * Ensures that the parameter isn't too short.
	 * <p>
	 * @param minLength the minimum length allowed for the string
	 * @return this
	 * @throws IllegalArgumentException if parameter is too short
	 */
	@Override
	public StringPreconditions hasMinimumLength(int minLength) throws IllegalArgumentException
	{
		if (parameter.length() >= minLength)
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least %d characters. Length was %d. Value was \"%s\".",
				name, minLength, parameter.length(), parameter));
	}

	/**
	 * Ensures that the parameter's length is equal to a single value.
	 * <p>
	 * @param length the expected length of the string
	 * @return this
	 * @throws IllegalArgumentException if parameter length is incorrect
	 */
	@Override
	public StringPreconditions hasLength(int length) throws IllegalArgumentException
	{
		if (parameter.length() == length)
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s's length must be equal to %d. Length was %d. Value was \"%s\".",
				name, length, parameter.length(), parameter));
	}

	/**
	 * Ensures that the parameter isn't too long.
	 * <p>
	 * @param maxLength the maximum length allowed for the string
	 * @return this
	 * @throws IllegalArgumentException if parameter is too long
	 */
	@Override
	public StringPreconditions hasMaximumLength(int maxLength) throws IllegalArgumentException
	{
		if (parameter.length() <= maxLength)
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be shorter than %d characters. Length was %d. Value was \"%s\".",
				name, maxLength + 1, parameter.length(), parameter));
	}

	/**
	 * Ensures that the parameter contains a valid email format.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not contain a valid email format
	 */
	@Override
	public StringPreconditions isEmailFormat() throws IllegalArgumentException
	{
		if (emailPattern.matcher(parameter).matches())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s does not contain a valid email format: %s", name, parameter));
	}

	/**
	 * Ensures that the parameter contains a valid IP address format.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not contain a valid IP address format
	 */
	@Override
	public StringPreconditions isIpAddressFormat() throws IllegalArgumentException
	{
		// IPv4 must start with a digit. IPv6 must start with a colon.
		char firstCharacter = parameter.charAt(0);
		if (Character.digit(firstCharacter, 16) == -1 && (firstCharacter != ':'))
		{
			return throwException(IllegalArgumentException.class,
				String.format("%s does not contain a valid IP address format: %s", name, parameter));
		}
		try
		{
			InetAddress.getByName(parameter);
		}
		catch (UnknownHostException e)
		{
			return throwException(IllegalArgumentException.class,
				String.format("%s does not contain a valid IP address format: %s", name, parameter), e);
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
	@Override
	public StringPreconditions startsWith(String prefix) throws IllegalArgumentException
	{
		if (parameter.startsWith(prefix))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must start with: %s", name, prefix));
	}

	/**
	 * Ensures that the parameter does not start with a value.
	 * <p>
	 * @param prefix the value the string must not start with
	 * @return this
	 * @throws IllegalArgumentException if the parameter starts with prefix
	 */
	@Override
	public StringPreconditions doesNotStartWith(String prefix) throws IllegalArgumentException
	{
		if (!parameter.startsWith(prefix))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not start with: %s", name, prefix));
	}

	/**
	 * Ensures that the parameter ends with a value.
	 * <p>
	 * @param suffix the value the string must end with
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not end with suffix
	 */
	@Override
	public StringPreconditions endsWith(String suffix) throws IllegalArgumentException
	{
		if (parameter.endsWith(suffix))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must end with: %s", name, suffix));
	}

	/**
	 * Ensures that the parameter does not end with a value.
	 * <p>
	 * @param suffix the value the string must not end with
	 * @return this
	 * @throws IllegalArgumentException if the parameter ends with suffix
	 */
	@Override
	public StringPreconditions doesNotEndWith(String suffix) throws IllegalArgumentException
	{
		if (!parameter.endsWith(suffix))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not end with: %s", name, suffix));
	}
}
