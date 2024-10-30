/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.scope;

import com.github.cowwoc.requirements10.java.internal.terminal.Terminal;

/**
 * The process configuration.
 * <p>
 * <b>Thread-safety</b>: Implementations must be thread-safe.
 */
public interface ProcessScope extends AutoCloseable
{
	/**
	 * @return the terminal attached to the process
	 */
	Terminal getTerminal();

	@Override
	void close();
}