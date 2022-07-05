/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.ThreadConfiguration;

import java.util.function.Supplier;

/**
 * The JVM configuration.
 * <p>
 * Implementations must be thread-safe.
 */
public interface JvmScope extends AutoCloseable
{
	/**
	 * @return the global configuration inherited by all verifiers
	 */
	GlobalConfiguration getGlobalConfiguration();

	/**
	 * @return the configuration shared by all verifiers invoked by the current thread
	 */
	Supplier<ThreadConfiguration> getThreadConfiguration();

	@Override
	void close();
}
