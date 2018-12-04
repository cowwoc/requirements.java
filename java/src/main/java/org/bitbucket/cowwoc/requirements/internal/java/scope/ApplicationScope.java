/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.scope;

import org.bitbucket.cowwoc.requirements.internal.java.util.Exceptions;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.Verifiers;
import org.bitbucket.cowwoc.requirements.java.terminal.TerminalEncoding;
import org.bitbucket.cowwoc.requirements.guava.GuavaVerifiers;
import org.bitbucket.cowwoc.requirements.internal.java.diff.DiffGenerator;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * The configuration of an application. A JVM may contain multiple applications.
 */
public interface ApplicationScope extends JvmScope
{
	/**
	 * @return the default configuration (value may change with every invocation)
	 */
	Supplier<Configuration> getDefaultConfiguration();

	/**
	 * @return the terminal encoding that verifiers will output (value may change with every
	 *         invocation)
	 */
	Supplier<TerminalEncoding> getTerminalEncoding();

	/**
	 * @return true if exceptions should show the difference between the actual and expected values
	 *         (value may change with every invocation)
	 */
	Supplier<Boolean> isDiffEnabled();

	/**
	 * @return true if exception stack-traces should contain elements from this API (value may change
	 *         with every invocation)
	 */
	Supplier<Boolean> isApiInStacktrace();

	DiffGenerator getDiffGenerator();

	Exceptions getExceptions();

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
