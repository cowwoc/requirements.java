/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

/**
 * Configures the behavior of all verifiers invoked by the current thread.
 * <p>
 * <p>
 * <b>Note</b>: This configuration is read by verifiers at every method invocation so updating it will
 * affect existing verifiers.
 */
public interface ThreadConfiguration
{
	/**
	 * Adds or updates contextual information associated with the exception message.
	 *
	 * @param name  the name of the parameter
	 * @param value the value of the parameter
	 * @return the updated configuration
	 * @throws NullPointerException if {@code name} is null
	 */
	ThreadConfiguration putContext(String name, Object value);

	/**
	 * Removes contextual information associated with the exception message.
	 *
	 * @param name the name of the parameter
	 * @return the updated configuration
	 * @throws NullPointerException if {@code name} is null
	 */
	ThreadConfiguration removeContext(String name);

	/**
	 * Returns the configuration shared by all verifiers.
	 *
	 * @return the configuration shared by all verifiers
	 */
	GlobalConfiguration getGlobalConfiguration();
}
