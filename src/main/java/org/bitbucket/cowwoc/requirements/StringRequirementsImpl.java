/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of StringRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class StringRequirementsImpl implements StringRequirements
{
	private final static Pattern EMAIL_PATTERN = Pattern.compile("[^@]+@[^@]+");
	private final String parameter;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<String> asObject;

	/**
	 * Creates new StringRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	StringRequirementsImpl(String parameter, String name,
		Configuration config) throws AssertionError
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectRequirementsImpl<>(parameter, name, config);
	}

	@Override
	public StringRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new StringRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public StringRequirements addContext(String key, Object value)
		throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new StringRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public StringRequirements withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new StringRequirementsImpl(parameter, name, newConfig);
	}

	/**
	 * Ensures that the parameter is empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not empty
	 * @see #trim()
	 */
	@Override
	public StringRequirements isEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", parameter).
			build();
	}

	/**
	 * Ensures that the parameter is not empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is empty
	 * @see #trim()
	 */
	@Override
	public StringRequirements isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be empty", name)).
			build();
	}

	/**
	 * Trims whitespace at the beginning and end of the parameter.
	 * <p>
	 * @return a StringRequirements with {@code parameter} trimmed
	 */
	@Override
	public StringRequirements trim()
	{
		String trimmed = parameter.trim();
		if (trimmed.equals(parameter))
			return this;
		return new StringRequirementsImpl(trimmed, name, config);
	}

	/**
	 * Ensures that the parameter contains a valid email format.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not contain a valid email format
	 */
	@Override
	public StringRequirements isEmailFormat() throws IllegalArgumentException
	{
		if (EMAIL_PATTERN.matcher(parameter).matches())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s does not contain a valid email format", name)).
			addContext("Actual", parameter).
			build();
	}

	/**
	 * Ensures that the parameter contains a valid IP address format.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not contain a valid IP address format
	 */
	@Override
	public StringRequirements isIpAddressFormat() throws IllegalArgumentException
	{
		// IPv4 must start with a digit. IPv6 must start with a colon.
		char firstCharacter = parameter.charAt(0);
		if (Character.digit(firstCharacter, 16) == -1 && (firstCharacter != ':'))
		{
			throw config.exceptionBuilder(IllegalArgumentException.class,
				String.format("%s does not contain a valid IP address format", name)).
				addContext("Actual", parameter).
				build();
		}
		try
		{
			InetAddress.getByName(parameter);
		}
		catch (UnknownHostException e)
		{
			throw config.exceptionBuilder(IllegalArgumentException.class,
				String.format("%s does not contain a valid IP address format", name), e).
				addContext("Actual", parameter).
				build();
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
	public StringRequirements startsWith(String prefix) throws IllegalArgumentException
	{
		if (parameter.startsWith(prefix))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must start with \"%s\".", name, prefix)).
			addContext("Actual", parameter).
			build();
	}

	/**
	 * Ensures that the parameter does not start with a value.
	 * <p>
	 * @param prefix the value the string must not start with
	 * @return this
	 * @throws IllegalArgumentException if the parameter starts with prefix
	 */
	@Override
	public StringRequirements doesNotStartWith(String prefix) throws IllegalArgumentException
	{
		if (!parameter.startsWith(prefix))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not start with \"%s\".", name, prefix)).
			addContext("Actual", parameter).
			build();
	}

	/**
	 * Ensures that the parameter ends with a value.
	 * <p>
	 * @param suffix the value the string must end with
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not end with suffix
	 */
	@Override
	public StringRequirements endsWith(String suffix) throws IllegalArgumentException
	{
		if (parameter.endsWith(suffix))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must end with \"%s\".", name, suffix)).
			addContext("Actual", parameter).
			build();
	}

	/**
	 * Ensures that the parameter does not end with a value.
	 * <p>
	 * @param suffix the value the string must not end with
	 * @return this
	 * @throws IllegalArgumentException if the parameter ends with suffix
	 */
	@Override
	public StringRequirements doesNotEndWith(String suffix) throws IllegalArgumentException
	{
		if (!parameter.endsWith(suffix))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not end with \"%s\".", name, suffix)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public StringLengthRequirements length()
	{
		return new StringLengthRequirementsImpl(parameter, name, config);
	}

	@Override
	public StringRequirements isEqualTo(String value) throws IllegalArgumentException
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public StringRequirements isEqualTo(String value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public StringRequirements isNotEqualTo(String value) throws IllegalArgumentException
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public StringRequirements isNotEqualTo(String value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public StringRequirements isIn(Collection<String> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public StringRequirements isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public StringRequirements isNull() throws IllegalArgumentException
	{
		asObject.isNull();
		return this;
	}

	@Override
	public StringRequirements isNotNull() throws NullPointerException
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public StringRequirements isolate(Consumer<StringRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
