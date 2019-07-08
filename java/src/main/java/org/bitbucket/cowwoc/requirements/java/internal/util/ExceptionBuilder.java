/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.util;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Builds an exception.
 *
 * @param <T> the type of exception being thrown
 */
public final class ExceptionBuilder<T extends Exception>
{
	private final ApplicationScope scope;
	private final Configuration config;
	private final Class<T> type;
	private Throwable cause;
	private final Exceptions exceptions;
	private final boolean cleanStackTrace;
	/**
	 * Contextual information associated with the exception (name-value pairs).
	 */
	private final List<Entry<String, Object>> context = new ArrayList<>(2);

	/**
	 * @param scope         the application configuration
	 * @param configuration a verifier's configuration
	 * @param type          the type of the exception
	 * @throws NullPointerException if {@code scope}, {@code configuration} or {@code type} are null
	 */
	public ExceptionBuilder(ApplicationScope scope, Configuration configuration, Class<T> type)
	{
		assert (scope != null) : "scope may not be null";
		assert (configuration != null) : "configuration may not be null";
		this.scope = scope;
		this.config = configuration;
		this.exceptions = scope.getExceptions();
		this.type = type;
		this.cleanStackTrace = scope.getGlobalConfiguration().isCleanStackTrace();
	}

	/**
	 * Sets the cause of the exception
	 *
	 * @param cause the underlying cause of the exception ({@code null} if absent)
	 * @return this
	 */
	public ExceptionBuilder<T> setCause(Throwable cause)
	{
		this.cause = cause;
		return this;
	}

	/**
	 * Adds contextual information to append to the exception message. Overrides any values
	 * associated with the {@code name} at the {@link Configuration} level.
	 *
	 * @param name  the name of the variable
	 * @param value the value of the variable
	 * @return this
	 * @throws NullPointerException if {@code name} is null
	 */
	public ExceptionBuilder<T> addContext(String name, Object value)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		context.add(new SimpleImmutableEntry<>(name, value));
		return this;
	}

	/**
	 * Adds contextual information to append to the exception message.
	 *
	 * @param context the list of name-value pairs to append to the exception message
	 * @return this
	 * @throws NullPointerException if {@code context} is null
	 */
	public ExceptionBuilder<T> addContext(List<Entry<String, Object>> context)
	{
		if (context == null)
			throw new NullPointerException("context may not be null");
		this.context.addAll(context);
		return this;
	}

	/**
	 * @param message the exception message
	 * @return the exception corresponding to the validation failure
	 * @throws AssertionError if {@code message} is null
	 */
	public T build(String message)
	{
		assert (message != null);

		Map<String, Object> localContext = config.getContext();
		assert (Maps.isUnmodifiable(localContext)) : "localContext may not be modifiable";

		Map<String, Object> threadContext = scope.getThreadConfiguration().get().getContext();
		assert (Maps.isUnmodifiable(threadContext)) : "threadContext may not be modifiable";

		List<Entry<String, Object>> mergedContext;
		if (localContext.isEmpty() && threadContext.isEmpty())
			mergedContext = context;
		else
		{
			mergedContext = new ArrayList<>(context.size() + threadContext.size() + localContext.size());
			Set<String> existingKeys = new HashSet<>();
			for (Entry<String, Object> entry : context)
			{
				mergedContext.add(entry);
				existingKeys.add(entry.getKey());
			}

			for (Entry<String, Object> entry : localContext.entrySet())
			{
				if (existingKeys.add(entry.getKey()))
					mergedContext.add(entry);
			}

			for (Entry<String, Object> entry : threadContext.entrySet())
			{
				if (existingKeys.add(entry.getKey()))
					mergedContext.add(entry);
			}
		}

		// null entries denote a newline between DIFF sections
		int maxKeyLength = 0;
		for (Entry<String, Object> entry : mergedContext)
		{
			if (entry == null)
				continue;
			int length = entry.getKey().length();
			if (length > maxKeyLength)
				maxKeyLength = length;
		}
		StringJoiner messageWithContext = new StringJoiner("\n");
		messageWithContext.add(message);
		for (Entry<String, Object> entry : mergedContext)
		{
			if (entry == null)
				messageWithContext.add("");
			else
				messageWithContext.add(alignLeft(entry.getKey(), maxKeyLength) + ": " +
					config.toString(entry.getValue()));
		}
		return exceptions.createException(type, messageWithContext.toString(), cause, cleanStackTrace);
	}

	/**
	 * @param text      the {@code String} to align
	 * @param minLength the minimum length of {@code text}
	 * @return {@code text} padded on the right with spaces until its length is greater than or equal to
	 * {@code minLength}
	 */
	private String alignLeft(String text, int minLength)
	{
		int actualLength = text.length();
		if (actualLength > minLength)
			return text;
		return text + " ".repeat(minLength - actualLength);
	}
}
