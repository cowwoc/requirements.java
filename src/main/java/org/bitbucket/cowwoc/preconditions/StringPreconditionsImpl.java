/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Default implementation of StringPreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class StringPreconditionsImpl extends AbstractObjectPreconditions<StringPreconditions, String>
	implements StringPreconditions
{
	private final static Pattern EMAIL_PATTERN = Pattern.compile("[^@]+@[^@]+");

	/**
	 * Creates new StringPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	StringPreconditionsImpl(String parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
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
		return throwException(IllegalArgumentException.class, String.format("%s must be empty.\n" +
			"Actual  : %s", name, parameter));
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
		return new StringPreconditionsImpl(parameter.trim(), name, exceptionOverride);
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
		if (EMAIL_PATTERN.matcher(parameter).matches())
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
			String.format("%s must start with \"%s\".\n" +
				"Actual  : \"%s\"", name, prefix, parameter));
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
			String.format("%s must not start with \"%s\".\n" +
				"Actual  : \"%s\"", name, prefix, parameter));
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
			String.format("%s must end with \"%s\".\n" +
				"Actual  : \"%s\"", name, suffix, parameter));
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
			String.format("%s must not end with \"%s\".\n" +
				"Actual  : \"%s\"", name, suffix, parameter));
	}

	@Override
	public StringLengthPreconditions length()
	{
		return new StringLengthPreconditionsImpl(parameter, name, exceptionOverride);
	}

	@Override
	protected StringPreconditions valueOf(String parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
	{
		return new StringPreconditionsImpl(parameter, name, exceptionOverride);
	}
}
