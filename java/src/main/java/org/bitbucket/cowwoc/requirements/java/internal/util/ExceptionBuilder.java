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
 */
public final class ExceptionBuilder
{
	private final ApplicationScope scope;
	private final Configuration config;
	private final String message;
	private String messageWithContext;
	private Throwable cause;
	/**
	 * Contextual information associated with the exception (name-value pairs).
	 */
	private final List<Entry<String, Object>> context = new ArrayList<>(2);

	/**
	 * @param scope         the application configuration
	 * @param configuration a validator's configuration
	 * @param message       the exception message
	 * @throws AssertionError if {@code scope}, {@code configuration}, {@code type}or {@code message}
	 *                        are null
	 */
	public ExceptionBuilder(ApplicationScope scope, Configuration configuration, String message)
	{
		assert (scope != null) : "scope may not be null";
		assert (configuration != null) : "configuration may not be null";
		assert (message != null);
		this.scope = scope;
		this.config = configuration;
		this.message = message;
	}

	/**
	 * Returns the underlying cause of the exception.
	 *
	 * @return the underlying cause of the exception
	 */
	public Throwable getCause()
	{
		return cause;
	}

	/**
	 * Sets the cause of the exception
	 *
	 * @param cause the underlying cause of the exception ({@code null} if absent)
	 * @return this
	 */
	public ExceptionBuilder setCause(Throwable cause)
	{
		this.cause = cause;
		return this;
	}

	/**
	 * Returns the exception message with contextual information.
	 *
	 * @return the exception message with contextual information
	 */
	public String getMessage()
	{
		if (messageWithContext == null)
		{
			Map<String, Object> configContext = config.getContext();
			assert (Maps.isUnmodifiable(configContext)) : "configContext may not be modifiable";

			Map<String, Object> threadContext = scope.getThreadConfiguration().get().getContext();
			assert (Maps.isUnmodifiable(threadContext)) : "threadContext may not be modifiable";

			List<Entry<String, Object>> mergedContext;
			if (configContext.isEmpty() && threadContext.isEmpty())
				mergedContext = context;
			else
			{
				mergedContext = new ArrayList<>(context.size() + threadContext.size() + configContext.size());
				Set<String> existingKeys = new HashSet<>();
				for (Entry<String, Object> entry : context)
				{
					mergedContext.add(entry);
					existingKeys.add(entry.getKey());
				}

				for (Entry<String, Object> entry : configContext.entrySet())
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
				{
					messageWithContext.add(alignLeft(entry.getKey(), maxKeyLength) + ": " +
						config.toString(entry.getValue()));
				}
			}
			this.messageWithContext = messageWithContext.toString();
		}
		return messageWithContext;
	}

	/**
	 * @param text      the {@code String} to align
	 * @param minLength the minimum length of {@code text}
	 * @return {@code text} padded on the right with spaces until its length is greater than or equal to
	 * {@code minLength}
	 */
	private static String alignLeft(String text, int minLength)
	{
		int actualLength = text.length();
		if (actualLength > minLength)
			return text;
		return text + " ".repeat(minLength - actualLength);
	}

	/**
	 * Returns the list of name-value pairs to append to the exception message.
	 *
	 * @return the list of name-value pairs to append to the exception message
	 */
	public List<Entry<String, Object>> getContext()
	{
		return context;
	}

	/**
	 * Adds contextual information associated with the failure.
	 *
	 * @param name  the name of a variable
	 * @param value the value of the variable
	 * @return this
	 * @throws AssertionError if {@code name} is null or empty
	 */
	public ExceptionBuilder addContext(String name, Object value)
	{
		assert (name != null) : "name may not be null";
		assert (!name.trim().isEmpty()) : "name may not be empty";
		context.add(new SimpleImmutableEntry<>(name, value));
		messageWithContext = null;
		return this;
	}

	/**
	 * Adds contextual information to append to the exception message.
	 *
	 * @param context the list of name-value pairs to append to the exception message
	 * @return this
	 * @throws AssertionError if {@code context} is null
	 */
	public ExceptionBuilder addContext(List<Entry<String, Object>> context)
	{
		assert (context != null) : "context may not be null";
		this.context.addAll(context);
		messageWithContext = null;
		return this;
	}

	/**
	 * @param type the type of the exception
	 * @return a new instance of the exception
	 */
	public <T extends Exception> T build(Class<T> type)
	{
		Exceptions exceptions = scope.getExceptions();
		boolean cleanStackTrace = scope.getGlobalConfiguration().isCleanStackTrace();
		return exceptions.createException(type, getMessage(), cause, cleanStackTrace);
	}

	@Override
	public String toString()
	{
		return "message: " + message + ", cause: " + cause;
	}
}
