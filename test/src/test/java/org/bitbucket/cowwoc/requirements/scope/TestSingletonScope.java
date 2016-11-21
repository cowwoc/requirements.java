/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.scope;

import java.util.Optional;
import org.bitbucket.cowwoc.requirements.RequirementVerifier;
import org.bitbucket.cowwoc.requirements.diff.string.TerminalType;

/**
 * SingletonScope for the test codebase.
 *
 * @author Gili Tzabari
 */
public final class TestSingletonScope extends AbstractSingletonScope
{
	private final Optional<TerminalType> requestedTerminalType;

	/**
	 * Equivalent to {@link #TestSingletonScope(TerminalType) TestSingletonScope(TerminalType.NONE)}.
	 */
	public TestSingletonScope()
	{
		this(TerminalType.NONE);
	}

	/**
	 * @param requestedTerminalType the terminal type that was requested by the user.
	 *                              {@code null} if the terminal type should be auto-detected
	 */
	public TestSingletonScope(TerminalType requestedTerminalType)
	{
		this.requestedTerminalType = Optional.ofNullable(requestedTerminalType);
	}

	@Override
	public Optional<TerminalType> getRequestedTerminalType()
	{
		return requestedTerminalType;
	}

	@Override
	public RequirementVerifier getDefaultVerifier()
	{
		return new RequirementVerifier(this);
	}

	@Override
	public void close()
	{
	}
}
