/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Configures the behavior of all verifiers invoked by the current thread.
 * <p>
 * <b>Note</b>: This configuration is read by verifiers at every method invocation so updating it will
 * affect existing verifiers.
 */
public final class ThreadRequirements
{
	// Must use a Supplier because the instance is thread-local
	private static final Supplier<ThreadConfiguration> DELEGATE =
		MainApplicationScope.INSTANCE.getThreadConfiguration();

	/**
	 * Prevent construction.
	 */
	private ThreadRequirements()
	{
	}

	/**
	 * Returns a map to append to the exception message.
	 *
	 * @return an unmodifiable map to append to the exception message
	 * @see #withContext(String, Object)
	 */
	public static Map<String, Object> getContext()
	{
		return DELEGATE.get().getContext();
	}

	/**
	 * Adds or updates contextual information associated with the exception message.
	 *
	 * @param name  the name of the parameter
	 * @param value the value of the parameter
	 * @return this
	 * @throws NullPointerException if {@code name} is null
	 */
	public static ThreadConfiguration withContext(String name, Object value)
	{
		return DELEGATE.get().withContext(name, value);
	}

	/**
	 * Removes contextual information associated with the exception message.
	 *
	 * @param name the name of the parameter
	 * @return this
	 * @throws NullPointerException if {@code name} is null
	 */
	public static ThreadConfiguration withoutContext(String name)
	{
		return DELEGATE.get().withoutContext(name);
	}

	/**
	 * Removes all contextual information associated with the exception message.
	 *
	 * @return this
	 */
	public static ThreadConfiguration withoutAnyContext()
	{
		return DELEGATE.get().withoutAnyContext();
	}
}