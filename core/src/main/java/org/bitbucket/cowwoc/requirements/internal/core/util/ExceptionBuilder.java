/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.util;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.StringJoiner;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * Builds an exception.
 *
 * @author Gili Tzabari
 */
public final class ExceptionBuilder
{
	private final Configuration config;
	private Class<? extends RuntimeException> type;
	private final String message;
	private final Throwable cause;
	private final boolean apiInStacktrace;
	/**
	 * Contextual information associated with the exception (name-value pairs).
	 */
	private final List<Entry<String, Object>> context = new ArrayList<>(2);

	/**
	 * Creates a new builder.
	 *
	 * @param configuration   the instance configuration
	 * @param type            the type of the exception
	 * @param message         the exception message
	 * @param cause           the underlying cause of the exception ({@code null} if absent)
	 * @param apiInStacktrace true if API elements should show up in the stacktrace
	 * @throws NullPointerException if {@code configuration} or {@code message} are null
	 */
	private ExceptionBuilder(Configuration configuration, Class<? extends RuntimeException> type,
		String message, Throwable cause, boolean apiInStacktrace)
	{
		if (configuration == null)
			throw new NullPointerException("configuration may not be null");
		if (message == null)
			throw new NullPointerException("message may not be null");
		this.config = configuration;
		this.type = config.getException().orElse(type);
		this.message = message;
		this.cause = cause;
		this.apiInStacktrace = apiInStacktrace;
	}

	/**
	 * Equivalent to
	 * {@link #ExceptionBuilder(Configuration, Class, String, Throwable, boolean) ExceptionBuilder(configuration, message, cause, scope.isApiInStacktrace().get())}.
	 *
	 * @param scope         the application configuration
	 * @param configuration a verifier's configuration
	 * @param type          the type of the exception
	 * @param message       the exception message
	 * @param cause         the underlying cause of the exception ({@code null} if absent)
	 * @throws NullPointerException if {@code scope}, {@code configuration}, {@code type} or message
	 *                              are null
	 */
	public ExceptionBuilder(ApplicationScope scope, Configuration configuration,
		Class<? extends RuntimeException> type, String message, Throwable cause)
	{
		this(configuration, type, message, cause, scope.isApiInStacktrace().get());
	}

	/**
	 * Equivalent to
	 * {@link #ExceptionBuilder(ApplicationScope, Configuration, Class, String) ExceptionBuilder(scope, configuration, message, null)}.
	 *
	 * @param scope         the application configuration
	 * @param configuration a verifier's configuration
	 * @param type          the type of the exception
	 * @param message       the exception message
	 * @throws NullPointerException if {@code scope}, {@code configuration}, {@code type} or message
	 *                              are null
	 */
	public ExceptionBuilder(ApplicationScope scope, Configuration configuration,
		Class<? extends RuntimeException> type, String message)
	{
		this(scope, configuration, type, message, null);
	}

	/**
	 * Adds contextual information to append to the exception message.
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
	 * @param context the name-value pairs to add
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

		List<Entry<String, Object>> contextPostfix = config.getContext();
		assert (Lists.isUnmodifiable(contextPostfix)): "contextPostfix may not be modifiable";

		List<Entry<String, Object>> mergedContext;
		if (contextPostfix.isEmpty())
			mergedContext = context;
		else
		{
			mergedContext = new ArrayList<>(context.size() + contextPostfix.size());
			mergedContext.addAll(context);
			mergedContext.addAll(contextPostfix);
		}

		int maxKeyLength = mergedContext.stream().filter(Objects::nonNull).map(Entry::getKey).
			mapToInt(String::length).max().orElse(0);
		for (Entry<String, Object> entry: mergedContext)
		{
			if (entry == null)
				messageWithContext.add("");
			else
				messageWithContext.add(String.format("%-" + maxKeyLength + "s: %s", entry.getKey(),
					config.toString(entry.getValue())));
		}
		return Exceptions.createException(type, messageWithContext.toString(), cause, apiInStacktrace);
	}
}
