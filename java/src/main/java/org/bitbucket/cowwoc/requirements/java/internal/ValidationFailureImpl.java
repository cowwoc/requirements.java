/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.Exceptions;
import org.bitbucket.cowwoc.requirements.java.internal.util.Maps;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Default implementation of {@code ValidationFailure}.
 */
public final class ValidationFailureImpl implements ValidationFailure
{
	private final ApplicationScope scope;
	private final Configuration config;
	private final Class<? extends Exception> exceptionType;
	private final String message;
	private String messageWithContext;
	private Throwable cause;
	/**
	 * The contextual information associated with the exception (name-value pairs).
	 */
	private final List<Entry<String, Object>> context = new ArrayList<>(2);

	/**
	 * @param scope         the application configuration
	 * @param config        the instance configuration
	 * @param exceptionType the type of exception associated with the failure
	 * @param message       the message associated with the failure
	 * @throws NullPointerException     if {@code validator}, {@code exceptionType} or {@code message} are null
	 * @throws IllegalArgumentException if {@code message} is empty
	 */
	public ValidationFailureImpl(ApplicationScope scope, Configuration config,
	                             Class<? extends Exception> exceptionType, String message)
	{
		assert (scope != null);
		assert (config != null);
		assert (exceptionType != null);
		assert (message != null);
		assert (!message.trim().isEmpty()) : "message may not be empty";

		this.scope = scope;
		this.config = config;
		this.exceptionType = exceptionType;
		this.message = message;
	}

	@Override
	public Class<? extends Exception> getExceptionType()
	{
		return exceptionType;
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
	 * Sets the underlying cause of the exception.
	 *
	 * @param cause the underlying cause of the exception
	 * @return this
	 */
	public ValidationFailureImpl setCause(Throwable cause)
	{
		this.cause = cause;
		return this;
	}

	@Override
	public String getMessage()
	{
		if (messageWithContext == null)
			this.messageWithContext = createMessageWithContext();
		return messageWithContext;
	}

	/**
	 * Returns the failure message with contextual information.
	 *
	 * @return the failure message with contextual information
	 */
	private String createMessageWithContext()
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
				if (entry != null)
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
		return messageWithContext.toString();
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
	 * Adds contextual information associated with the failure.
	 *
	 * @param name  the name of a variable
	 * @param value the value of the variable
	 * @return this
	 * @throws AssertionError if {@code name} is null or empty
	 */
	public ValidationFailureImpl addContext(String name, Object value)
	{
		assert (name != null) : "name may not be null";
		assert (!name.trim().isEmpty()) : "name may not be empty";
		context.add(new SimpleImmutableEntry<>(name, value));
		messageWithContext = null;
		return this;
	}

	/**
	 * Adds contextual information to append to the message.
	 *
	 * @param context the list of name-value pairs to append to the exception message
	 * @return this
	 * @throws NullPointerException if {@code context} is null
	 */
	public ValidationFailureImpl addContext(List<Entry<String, Object>> context)
	{
		assert (context != null) : "context may not be null";
		this.context.addAll(context);
		messageWithContext = null;
		return this;
	}

	/**
	 * Creates an exception that corresponds to with this failure.
	 *
	 * @param <T>  type the type of exception to create
	 * @param type the type of exception to create
	 * @return the created exception
	 */
	public <T extends Exception> T createException(Class<T> type)
	{
		Exceptions exceptions = scope.getExceptions();
		boolean cleanStackTrace = scope.getGlobalConfiguration().isCleanStackTrace();
		return exceptions.createException(type, getMessage(), cause, cleanStackTrace);
	}

	@Override
	public String toString()
	{
		return "exceptionType: " + exceptionType + ", message: " + getMessage() + ", cause: " + cause;
	}
}