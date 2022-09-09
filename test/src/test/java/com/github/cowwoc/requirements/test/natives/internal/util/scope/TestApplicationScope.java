/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.natives.internal.util.scope;

import com.github.cowwoc.requirements.java.GlobalConfiguration;
import com.github.cowwoc.requirements.java.internal.scope.AbstractApplicationScope;
import com.github.cowwoc.requirements.natives.terminal.TerminalEncoding;

/**
 * ApplicationScope for the test codebase.
 */
public final class TestApplicationScope extends AbstractApplicationScope
{
	private final GlobalConfiguration globalConfiguration;

	/**
	 * @param terminalEncoding the type of encoding that verifiers should output
	 * @throws NullPointerException if {@code terminalEncoding} is null
	 */
	public TestApplicationScope(TerminalEncoding terminalEncoding)
	{
		// Show the full stack trace in case of failure
		this.globalConfiguration = new TestGlobalConfiguration(terminalEncoding).
			withoutCleanStackTrace();
	}

	/**
	 * @param terminalEncoding the type of encoding that verifiers should output
	 * @param terminalWidth    the terminal width that the test should use
	 * @throws NullPointerException     if {@code terminalEncoding} is null
	 * @throws IllegalArgumentException if {@code terminalWidth} is zero or negative
	 */
	public TestApplicationScope(TerminalEncoding terminalEncoding, int terminalWidth)
	{
		this.globalConfiguration = new TestGlobalConfiguration(terminalEncoding, terminalWidth);
	}

	@Override
	public GlobalConfiguration getGlobalConfiguration()
	{
		return globalConfiguration;
	}

	@Override
	public void close()
	{
	}
}