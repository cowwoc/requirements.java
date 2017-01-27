/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.scope;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.CoreUnifiedVerifier;
import org.bitbucket.cowwoc.requirements.core.diff.DiffGenerator;
import org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding;

/**
 * An application configuration.
 *
 * @author Gili Tzabari
 */
public interface ApplicationScope extends JvmScope
{
	/**
	 * @return the default configuration
	 */
	Configuration getDefaultConfiguration();

	/**
	 * Returns the terminal encoding that verifiers will output.
	 * <p>
	 * The first time this method is invoked it reads the value of
	 * {@code JvmScope.getTerminal().getEncoding()} and returns it for the remainder of the
	 * application lifetime.
	 *
	 * @return the terminal encoding that verifiers will output
	 */
	TerminalEncoding getTerminalEncoding();

	DiffGenerator getDiffGenerator();

	/**
	 * Returns a verifier that can be used to check a verifier's own parameters.
	 *
	 * @return a {@link CoreUnifiedVerifier} that uses this scope
	 */
	CoreUnifiedVerifier getInternalVerifier();

	@Override
	void close();
}
