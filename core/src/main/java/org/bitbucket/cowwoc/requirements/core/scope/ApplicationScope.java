/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.scope;

import java.util.Optional;
import org.bitbucket.cowwoc.requirements.core.Verifiers;
import org.bitbucket.cowwoc.requirements.core.diff.DiffGenerator;
import org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding;
import org.bitbucket.cowwoc.requirements.guava.GuavaVerifiers;

/**
 * An application configuration.
 *
 * @author Gili Tzabari
 */
public interface ApplicationScope extends JvmScope
{
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

	/**
	 * Indicates if exceptions should show the difference between the actual and expected values.
	 * <p>
	 * The first time this method is invoked it reads the value of
	 * {@code JvmScope.getGlobalConfiguration().isDiffEnabled()} and returns it for the remainder
	 * of the application lifetime.
	 *
	 * @return true if exceptions should show the difference between the actual and expected values
	 *         (true by default)
	 */
	boolean isDiffEnabled();

	/**
	 * Indicates if exception stack-traces should contain elements from this API.
	 * <p>
	 * The first time this method is invoked it reads the value of
	 * {@code JvmScope.getGlobalConfiguration().isApiInStacktrace()} and returns it for the remainder
	 * of the application lifetime.
	 *
	 * @return true if exception stack-traces should contain elements from this API (false by default)
	 */
	boolean isApiInStacktrace();

	DiffGenerator getDiffGenerator();

	/**
	 * @return a verifier that can be used to check a verifier's own parameters
	 */
	Verifiers getInternalVerifier();

	/**
	 * Returns a new instance of {@code GuavaVerifiers}.
	 *
	 * @return {@code Optional.empty()} if the guava module is not available
	 */
	Optional<GuavaVerifiers> createGuavaVerifiers();

	@Override
	void close();
}
