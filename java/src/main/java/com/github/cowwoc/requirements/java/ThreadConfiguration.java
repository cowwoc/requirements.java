/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import java.util.Map;

/**
 * Configures the behavior of all verifiers invoked by the current thread.
 * <p>
 * <b>Note</b>: This configuration is read by verifiers at every method invocation so updating it will
 * affect existing verifiers.
 */
public interface ThreadConfiguration
{
	/**
	 * Returns a map to append to the exception message.
	 *
	 * @return an unmodifiable map to append to the exception message
	 * @see #withContext(String, Object)
	 */
	Map<String, Object> getContext();

	/**
	 * Adds or updates contextual information associated with the exception message.
	 *
	 * @param name  the name of the parameter
	 * @param value the value of the parameter
	 * @return this
	 * @throws NullPointerException if {@code name} is null
	 */
	ThreadConfiguration withContext(String name, Object value);

	/**
	 * Removes contextual information associated with the exception message.
	 *
	 * @param name the name of the parameter
	 * @return this
	 * @throws NullPointerException if {@code name} is null
	 */
	ThreadConfiguration withoutContext(String name);

	/**
	 * Removes all contextual information associated with the exception message.
	 *
	 * @return this
	 */
	ThreadConfiguration withoutAnyContext();
}
