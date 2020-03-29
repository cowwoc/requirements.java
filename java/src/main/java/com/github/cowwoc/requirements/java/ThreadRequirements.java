/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.internal.scope.DefaultJvmScope;
import com.github.cowwoc.requirements.java.internal.scope.ThreadConfiguration;

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
	 */
	public static ThreadRequirements putContext(String name, Object value)
	{
		DELEGATE.get().putContext(name, value);
		return INSTANCE;
	}

	/**
	 * Removes contextual information associated with the exception message.
	 *
	 * @param name the name of the parameter
	 * @return this
	 * @throws NullPointerException if {@code name} is null
	 */
	public static ThreadRequirements removeContext(String name)
	{
		DELEGATE.get().removeContext(name);
		return INSTANCE;
	}

	/**
	 * Removes all contextual information associated with the exception message.
	 *
	 * @return this
	 */
	public static ThreadRequirements removeAllContext()
	{
		DELEGATE.get().removeAllContext();
		return INSTANCE;
	}
}
