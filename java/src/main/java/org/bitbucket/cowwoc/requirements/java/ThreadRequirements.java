/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.internal.scope.MainThreadConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ThreadConfiguration;

/**
 * Configures the behavior of all verifiers invoked by the current thread.
 * <p>
 * <p>
 * <b>Note</b>: This configuration is read by verifiers at every method invocation so updating it will
 * affect existing verifiers.
 */
public final class ThreadRequirements
{
	private static final ThreadLocal<ThreadConfiguration> DELEGATE =
		ThreadLocal.withInitial(MainThreadConfiguration::new);
	private static final ThreadRequirements INSTANCE = new ThreadRequirements();

	/**
	 * Prevent construction.
	 */
	private ThreadRequirements()
	{
	}

	/**
	 * Adds or updates contextual information associated with the exception message.
	 *
	 * @param name  the name of the parameter
	 * @param value the value of the parameter
	 * @return the updated configuration
	 * @throws NullPointerException if {@code name} is null
	 */
	public static ThreadRequirements putContext(String name, Object value)
	{
		ThreadConfiguration oldConfig = DELEGATE.get();
		ThreadConfiguration newConfig = oldConfig.putContext(name, value);
		if (!newConfig.equals(oldConfig))
			DELEGATE.set(newConfig);
		return INSTANCE;
	}

	/**
	 * Removes contextual information associated with the exception message.
	 *
	 * @param name the name of the parameter
	 * @return the updated configuration
	 * @throws NullPointerException if {@code name} is null
	 */
	public static ThreadRequirements removeContext(String name)
	{
		ThreadConfiguration oldConfig = DELEGATE.get();
		ThreadConfiguration newConfig = oldConfig.removeContext(name);
		if (!newConfig.equals(oldConfig))
			DELEGATE.set(newConfig);
		return INSTANCE;
	}
}
