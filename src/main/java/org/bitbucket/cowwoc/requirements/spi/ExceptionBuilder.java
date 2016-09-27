/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringJoiner;
import org.bitbucket.cowwoc.requirements.util.Exceptions;

/**
 * Builds an exception.
 *
 * @author Gili Tzabari
 */
@Beta
public final class ExceptionBuilder
{
	private final String message;
	private final List<Entry<String, Object>> contextPostfix;
	private final Throwable cause;
	private Class<? extends RuntimeException> type;
	/**
	 * Contextual information associated with the exception (key-value pairs).
	 */
	private final List<Entry<String, Object>> context = new ArrayList<>(2);

	/**
	 * Creates a new builder.
	 *
	 * @param type           the type of the exception
	 * @param message        the exception message
	 * @param contextPostfix the key-value pairs to append to the context set by the user
	 * @param cause          the underlying cause of the exception ({@code null} if absent)
	 * @throws NullPointerException if {@code type}, {@code message} or {@code contextPostfix} are
	 *                              null
	 */
	public ExceptionBuilder(Class<? extends RuntimeException> type, String message,
		Throwable cause, List<Entry<String, Object>> contextPostfix) throws NullPointerException
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (message == null)
			throw new NullPointerException("message may not be null");
		if (contextPostfix == null)
			throw new NullPointerException("contextPostfix may not be null");
		this.type = type;
		this.message = message;
		this.cause = cause;
		this.contextPostfix = ImmutableList.copyOf(contextPostfix);
	}

	/**
	 * @param type the type of exception to build
	 * @return this
	 * @throws NullPointerException if {@code type} is null
	 */
	public ExceptionBuilder type(Class<? extends RuntimeException> type)
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		this.type = type;
		return this;
	}

	/**
	 * Adds contextual information to append to the exception message.
	 *
	 * @param key   a key
	 * @param value a value
	 * @return this
	 * @throws NullPointerException if {@code key} is null
	 */
	public ExceptionBuilder addContext(String key, Object value) throws NullPointerException
	{
		if (key == null)
			throw new NullPointerException("key may not be null");
		context.add(new AbstractMap.SimpleImmutableEntry<>(key, value));
		return this;
	}

	/**
	 * Adds contextual information to append to the exception message.
	 *
	 * @param context the key-value pairs to add
	 * @return this
	 * @throws NullPointerException if {@code context} is null
	 */
	public ExceptionBuilder addContext(List<Entry<String, Object>> context)
		throws NullPointerException
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

		List<Entry<String, Object>> mergedContext;
		if (contextPostfix.isEmpty())
			mergedContext = context;
		else
		{
			mergedContext = ImmutableList.<Entry<String, Object>>builder().
				addAll(context).
				addAll(contextPostfix).
				build();
		}

		int maxKeyLength = mergedContext.stream().map(Entry::getKey).mapToInt(String::length).max().
			orElse(0);
		for (Entry<String, Object> entry: mergedContext)
		{
			messageWithContext.add(String.format("%-" + maxKeyLength + "s: %s", entry.getKey(),
				entry.getValue()));
		}
		return Exceptions.createException(type, messageWithContext.toString(), cause);
	}
}
