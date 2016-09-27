/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableList;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Determines the behavior of a requirements verifier.
 *
 * @author Gili Tzabari
 */
@Beta
public final class Configuration
{
	private static Configuration initial = new Configuration();

	/**
	 * @return the initial configuration without an exception override or contextual information
	 */
	public static Configuration initial()
	{
		return initial;
	}
	private final Class<? extends RuntimeException> exceptionOverride;
	private final ImmutableList<Entry<String, Object>> context;

	/**
	 * Creates a new instance without an exception override or contextual information.
	 */
	private Configuration()
	{
		this.exceptionOverride = null;
		this.context = ImmutableList.of();
	}

	/**
	 * Creates a new instance.
	 *
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @param context           key-value pairs to append to the exception message
	 * @throws NullPointerException if {@code context} is null
	 */
	public Configuration(Class<? extends RuntimeException> exceptionOverride,
		List<Entry<String, Object>> context) throws NullPointerException
	{
		assert (context != null);
		this.exceptionOverride = exceptionOverride;
		this.context = ImmutableList.copyOf(context);
	}

	/**
	 * Overrides the type of exception that will get thrown if a requirement fails.
	 * <p>
	 * @param exception the type of exception to throw, null to disable the override
	 * @return a configuration with the specified exception override
	 * @see #getExceptionOverride()
	 */
	public Configuration withException(Class<? extends RuntimeException> exception)
	{
		if (Objects.equals(exception, exceptionOverride))
			return this;
		return new Configuration(exception, context);
	}

	/**
	 * @return the type of exception to throw, null to disable the override
	 * @see #withException(Class)
	 */
	public Class<? extends RuntimeException> getExceptionOverride()
	{
		return exceptionOverride;
	}

	/**
	 * Appends a key-value pair to the exception message.
	 *
	 * @param key   a key
	 * @param value a value
	 * @return this
	 * @throws NullPointerException if {@code key} is null
	 * @see #getContext()
	 */
	public Configuration addContext(String key, Object value) throws NullPointerException
	{
		if (key == null)
			throw new NullPointerException("key may not be null");
		List<Entry<String, Object>> newContext = ImmutableList.<Entry<String, Object>>builder().
			addAll(context).
			add(new SimpleImmutableEntry<>(key, value)).
			build();
		return new Configuration(exceptionOverride, newContext);
	}

	/**
	 * Sets the contextual information to append to the exception message.
	 *
	 * @param context the contextual information
	 * @return a configuration with the specified context
	 * @throws NullPointerException
	 */
	public Configuration withContext(List<Entry<String, Object>> context) throws NullPointerException
	{
		if (context == null)
			throw new NullPointerException("context may not be null");
		if (Objects.equals(context, this.context))
			return this;
		return new Configuration(exceptionOverride, context);
	}

	/**
	 * @return key-value pairs to append to the exception message
	 * @see #addContext(String, Object)
	 */
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public ImmutableList<Entry<String, Object>> getContext()
	{
		return context;
	}

	/**
	 * Builds an exception.
	 * <p>
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @throws NullPointerException if {@code type} or {@code message} are null
	 * @return the exception builder
	 */
	public ExceptionBuilder exceptionBuilder(Class<? extends RuntimeException> type, String message)
	{
		return exceptionBuilder(type, message, null);
	}

	/**
	 * Builds an exception.
	 * <p>
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @param cause   the underlying cause of the exception ({@code null} if absent)
	 * @throws NullPointerException if {@code type} or {@code message} are null
	 * @return the exception builder
	 */
	public ExceptionBuilder exceptionBuilder(Class<? extends RuntimeException> type, String message,
		Throwable cause)
	{
		ExceptionBuilder result = new ExceptionBuilder(type, message, cause, context);
		if (exceptionOverride != null)
			result.type(exceptionOverride);
		return result;
	}
}
