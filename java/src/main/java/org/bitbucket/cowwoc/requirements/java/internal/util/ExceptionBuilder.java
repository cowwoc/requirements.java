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
	private final Exceptions exceptions;
	private Class<? extends RuntimeException> type;
	private final String message;
	private final Throwable cause;
	private final boolean cleanStackTrace;
	/**
	 * Contextual information associated with the exception (name-value pairs).
	 */
	private final List<Entry<String, Object>> context = new ArrayList<>(2);

	/**
	 * @param scope           the application configuration
	 * @param configuration   the instance configuration
	 * @param exceptions      an instance of {@code Exceptions}
	 * @param type            the type of the exception
	 * @param message         the exception message
	 * @param cause           the underlying cause of the exception ({@code null} if absent)
	 * @param cleanStackTrace true if stack traces should omit references to this library
	 * @throws AssertionError if {@code configuration}, {@code exceptions} or {@code message} are null
	 */
	private ExceptionBuilder(ApplicationScope scope, Configuration configuration, Exceptions exceptions,
	                         Class<? extends RuntimeException> type, String message,
	                         Throwable cause, boolean cleanStackTrace)
	{
		assert (scope != null) : "scope may not be null";
		assert (configuration != null) : "configuration may not be null";
		assert (exceptions != null) : "exceptions may not be null";
		assert (message != null) : "message may not be null";
		this.scope = scope;
		this.config = configuration;
		this.exceptions = exceptions;
		this.type = config.getException().orElse(type);
		this.message = message;
		this.cause = cause;
		this.cleanStackTrace = cleanStackTrace;
	}

	/**
	 * Equivalent to
	 * {@link #ExceptionBuilder(ApplicationScope, Configuration, Exceptions, Class, String, Throwable, boolean)
	 * ExceptionBuilder(scope, configuration, scope.getExceptions(), type, message, cause,
	 * scope.isCleanStackTrace().get())}.
	 *
	 * @param scope         the application configuration
	 * @param configuration a verifier's configuration
	 * @param type          the type of the exception
	 * @param message       the exception message
	 * @param cause         the underlying cause of the exception ({@code null} if absent)
	 * @throws NullPointerException if {@code scope}, {@code configuration}, {@code type} or message are null
	 */
	public ExceptionBuilder(ApplicationScope scope, Configuration configuration,
	                        Class<? extends RuntimeException> type, String message,
	                        Throwable cause)
	{
		this(scope, configuration, scope.getExceptions(), type, message, cause,
			scope.getGlobalConfiguration().isCleanStackTrace());
	}

	/**
	 * Equivalent to
	 * {@link #ExceptionBuilder(ApplicationScope, Configuration, Class, String, Throwable)
	 * ExceptionBuilder(scope, configuration, type, message, null)}.
	 *
	 * @param scope         the application configuration
	 * @param configuration a verifier's configuration
	 * @param type          the type of the exception
	 * @param message       the exception message
	 * @throws NullPointerException if {@code scope}, {@code configuration}, {@code type} or message are null
	 */
	public ExceptionBuilder(ApplicationScope scope, Configuration configuration,
	                        Class<? extends RuntimeException> type, String message)
	{
		this(scope, configuration, type, message, null);
	}

	/**
	 * Adds contextual information to append to the exception message. Overrides any values
	 * associated with the {@code name} at the {@link Configuration} level.
	 *
	 * @param name  the name of the value
	 * @param value a value
	 * @return this
	 * @throws NullPointerException if {@code name} is null
	 */
	public ExceptionBuilder addContext(String name, Object value)
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
	public ExceptionBuilder addContext(List<Entry<String, Object>> context)
	{
		if (context == null)
			throw new NullPointerException("context may not be null");
		this.context.addAll(context);
		return this;
	}

	/**
	 * @return a new exception
	 */
	public RuntimeException build()
	{
		StringJoiner messageWithContext = new StringJoiner("\n");
		messageWithContext.add(message);

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
