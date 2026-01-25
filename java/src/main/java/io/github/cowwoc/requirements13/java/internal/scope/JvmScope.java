/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.scope;

import io.github.cowwoc.pouch10.core.Scope;
import io.github.cowwoc.requirements13.java.internal.terminal.Terminal;

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