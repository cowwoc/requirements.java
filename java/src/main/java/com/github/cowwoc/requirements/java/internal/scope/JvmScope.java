/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.internal.terminal.Terminal;
import com.github.cowwoc.requirements.java.internal.util.Exceptions;

/**
 * The JVM configuration.
 * <p>
 * Implementations must be thread-safe.
 */
public interface JvmScope extends AutoCloseable
{
	/**
	 * @return an instance of {@code Exceptions}
	 */
	Exceptions getExceptions();

	/**
	 * @return the terminal attached to the process
	 */
	Terminal getTerminal();

	@Override
	void close();
}
