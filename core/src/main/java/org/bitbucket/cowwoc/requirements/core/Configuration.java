/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

/**
 * A verifier's configuration.
 *
 * @author Gili Tzabari
 * @see Configurable
 */
public final class Configuration
{
	private final List<Entry<String, Object>> context = new ArrayList<>(2);
	private Optional<Class<? extends RuntimeException>> exception = Optional.empty();

	/**
	 * Returns the type of exception that will be thrown if a verification fails.
	 *
	 * @return {@code Optional.empty()} if the default exception type will be thrown
	 * @see #withException(Class)
	 * @see #withDefaultException()
	 */
	public Optional<Class<? extends RuntimeException>> getException()
	{
		return exception;
	}

	/**
	 * Overrides the type of exception that will get thrown if a verification fails.
	 * <p>
	 * The exception class must define the following constructors:
	 * <p>
	 * {@code <init>(String message)}<br>
	 * {@code <init>(String message, Throwable cause)}
	 *
	 * @param exception the type of exception to throw
	 * @return this
	 * @throws NullPointerException if {@code exception} is null
	 */
	public Configuration withException(Class<? extends RuntimeException> exception)
	{
		if (exception == null)
			throw new NullPointerException("exception may not be null");
		this.exception = Optional.of(exception);
		return this;
	}

	/**
	 * Throws the default exception type if a verification fails.
	 *
	 * @return this
	 */
	public Configuration withDefaultException()
	{
		this.exception = Optional.empty();
		return this;
	}

	/**
	 * @return an unmodifiable list of key-value pairs to append to the exception message
	 * @see #addContext(String, Object)
	 */
	public List<Entry<String, Object>> getContext()
	{
		return Collections.unmodifiableList(context);
	}

	/**
	 * Appends contextual information to the exception message.
	 *
	 * @param key   a key
	 * @param value a value
	 * @return this
	 * @throws NullPointerException if {@code key} is null
	 */
	public Configuration addContext(String key, Object value)
	{
		if (key == null)
			throw new NullPointerException("key may not be null");
		context.add(new SimpleImmutableEntry<>(key, value));
		return this;
	}
}
