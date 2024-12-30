/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.scope;

import com.github.cowwoc.pouch.core.Scope;
import com.github.cowwoc.requirements10.java.internal.terminal.Terminal;

/**
 * Values specific to the lifetime of a JVM.
 * <p>
 * Implementations must be thread-safe.
 */
public interface JvmScope extends Scope
{
	/**
	 * @return the terminal that the JVM will output to
	 */
	Terminal getTerminal();

	@Override
	void close();
}