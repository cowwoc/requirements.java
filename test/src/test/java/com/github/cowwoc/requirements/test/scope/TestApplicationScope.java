/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.scope;

import com.github.cowwoc.requirements.java.internal.scope.AbstractApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.DefaultProcessScope;
import com.github.cowwoc.requirements.java.terminal.TerminalEncoding;

/**
 * ApplicationScope for the test codebase.
 */
public final class TestApplicationScope extends AbstractApplicationScope
{
	/**
	 * @param terminalEncoding the type of encoding that validators should output
	 * @throws NullPointerException if any of the arguments are null
	 */
	public TestApplicationScope(TerminalEncoding terminalEncoding)
	{
		// Show the full stack trace in case of failure
		super(DefaultProcessScope.INSTANCE, new TestGlobalConfiguration(terminalEncoding));
	}

	@Override
	public void close()
	{
	}
}