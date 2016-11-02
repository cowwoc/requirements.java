/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.scope;

import java.util.Optional;
import org.bitbucket.cowwoc.requirements.diff.string.DiffGenerator;
import org.bitbucket.cowwoc.requirements.diff.string.Terminal;
import org.bitbucket.cowwoc.requirements.diff.string.TerminalType;

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

	@Override
	void close();
}
