/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.internal.scope.DefaultJvmScope;

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
	private static final Supplier<ThreadConfiguration> DELEGATE =
		DefaultJvmScope.INSTANCE.getThreadConfiguration();
	// TODO: Remove alongside deprecated methods
	private static final ThreadRequirements INSTANCE = new ThreadRequirements();

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
	 * @see #putContext(String, Object)
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
	 * @deprecated Use {@link #withContext(String, Object)}
	 */
	@Deprecated
	public static ThreadRequirements putContext(String name, Object value)
	{
		withContext(name, value);
		return INSTANCE;
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
	 * @deprecated Use {@link #withoutContext(String)}
	 */
	@Deprecated
	public static ThreadRequirements removeContext(String name)
	{
		withoutContext(name);
		return INSTANCE;
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
	 * @deprecated Use {@link #withoutAnyContext()}
	 */
	@Deprecated
	public static ThreadRequirements removeAllContext()
	{
		withoutAnyContext();
		return INSTANCE;
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
