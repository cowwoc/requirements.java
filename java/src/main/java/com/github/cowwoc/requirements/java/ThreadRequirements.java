/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.internal.scope.DefaultThreadConfiguration;
import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;

import java.util.Map;

/**
 * Configures the behavior of all verifiers invoked by the current thread.
 * <p>
 * <b>Note</b>: This configuration is read by verifiers at every method invocation so updating it will
 * affect existing verifiers.
 */
public final class ThreadRequirements
{
	private static final ThreadConfiguration DELEGATE =
		new DefaultThreadConfiguration(MainApplicationScope.INSTANCE);

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
		return DELEGATE.getContext();
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
		return DELEGATE.withContext(name, value);
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
		return DELEGATE.withoutContext(name);
	}

	/**
	 * Removes all contextual information associated with the exception message.
	 *
	 * @return this
	 */
	public static ThreadConfiguration withoutAnyContext()
	{
		return DELEGATE.withoutAnyContext();
	}

	/**
	 * Returns the contextual information associated with this thread.
	 *
	 * @param message the exception message ({@code null} if absent)
	 * @return the contextual information associated with this thread
	 */
	public static String getContextMessage(String message)
	{
		return DELEGATE.getContextMessage(message);
	}
}