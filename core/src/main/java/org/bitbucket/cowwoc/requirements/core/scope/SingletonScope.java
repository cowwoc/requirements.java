/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.scope;

import java.util.Optional;
import org.bitbucket.cowwoc.requirements.core.UnifiedVerifier;
import org.bitbucket.cowwoc.requirements.core.diff.DiffGenerator;
import org.bitbucket.cowwoc.requirements.core.diff.Terminal;
import org.bitbucket.cowwoc.requirements.core.diff.TerminalType;

/**
 * The system configuration.
 *
 * @author Gili Tzabari
 */
public interface SingletonScope extends AutoCloseable
{
	DiffGenerator getDiffGenerator();

	Terminal getTerminal();

	/**
	 * Returns the terminal type that was requested by the user. This feature overrides
	 * {@link TerminalType#detected()} and can be used to force the use of ANSI sequences even when
	 * their support is not detected.
	 *
	 * @return {@code Optional.empty()} if the terminal type should be auto-detected
	 */
	Optional<TerminalType> getRequestedTerminalType();

	/**
	 * Returns a verifier that can be used to check a verifier's own parameters.
	 *
	 * @return a {@link UnifiedVerifier} that uses this scope
	 */
	UnifiedVerifier getInternalVerifier();

	@Override
	void close();
}
