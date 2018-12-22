/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

import org.bitbucket.cowwoc.requirements.java.GlobalConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.terminal.Terminal;

/**
 * The JVM configuration.
 * <p>
 * Implementations must be thread-safe.
 */
public interface JvmScope extends AutoCloseable
{
	/**
	 * @return the terminal attached to the process
	 */
	Terminal getTerminal();

	/**
	 * @return the global configuration inherited by all verifiers
	 */
	GlobalConfiguration getGlobalConfiguration();

	@Override
	void close();
}
