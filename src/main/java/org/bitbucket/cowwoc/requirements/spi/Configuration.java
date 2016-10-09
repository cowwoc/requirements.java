/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import org.bitbucket.cowwoc.requirements.Verifier;
import org.bitbucket.cowwoc.requirements.util.Lists;

/**
 * Determines the behavior of a requirements verifier.
 *
 * @author Gili Tzabari
 */
public final class Configuration implements Verifier
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
	private final List<Entry<String, Object>> context;

	/**
	 * Creates a new instance without an exception override or contextual information.
	 */
	private Configuration()
	{
		this.exceptionOverride = null;
		this.context = Collections.emptyList();
	}

	/**
	 * Creates a new instance.
	 *
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @param context           key-value pairs to append to the exception message
	 * @throws AssertionError if {@code context} is null; if {@code context} is modifiable
	 */
	private Configuration(Class<? extends RuntimeException> exceptionOverride,
		List<Entry<String, Object>> context) throws AssertionError
	{
		assert (context != null): "context may not be null";
		assert (Lists.isUnmodifiable(context)): "context may not be modifiable";
		this.exceptionOverride = exceptionOverride;
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return a configuration with the specified exception override
	 * @see #getException()
	 */
	@Override
	public Configuration withException(Class<? extends RuntimeException> exception)
	{
		if (Objects.equals(exception, exceptionOverride))
			return this;
		return new Configuration(exception, context);
	}

	/**
	 * @return the type of exception to throw, {@code null} to throw the default exception type
	 * @see #withException(Class)
	 */
	public Class<? extends RuntimeException> getException()
	{
		return exceptionOverride;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see #getContext()
	 */
	@Override
	public Configuration addContext(String key, Object value) throws NullPointerException
	{
		if (key == null)
			throw new NullPointerException("key may not be null");
		List<Entry<String, Object>> newContext = new ArrayList<>(context.size() + 1);
		newContext.addAll(context);
		newContext.add(new SimpleImmutableEntry<>(key, value));
		return new Configuration(exceptionOverride, Collections.unmodifiableList(newContext));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return a configuration with the specified context
	 * @see #getContext()
	 */
	@Override
	public Configuration withContext(List<Entry<String, Object>> context) throws NullPointerException
	{
		if (context == null)
			throw new NullPointerException("context may not be null");
		if (Objects.equals(context, this.context))
			return this;
		return new Configuration(exceptionOverride, context);
	}

	/**
	 * @return an unmodifiable list of key-value pairs to append to the exception message
	 * @see #addContext(String, Object)
	 */
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<Entry<String, Object>> getContext()
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
