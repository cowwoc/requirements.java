/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java.internal.terminal;

import com.github.cowwoc.requirements10.java.TerminalEncoding;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.terminal.Terminal;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

public final class TerminalTest
{
	/**
	 * Force validators to output using an encoding that might not be supported by the terminal.
	 */
	@Test
	public void forceUnsupportedEncoding()
	{
		try (ApplicationScope application = new TestApplicationScope(NONE))
		{
			Terminal terminal = application.getTerminal();
			terminal.setEncoding(TerminalEncoding.RGB_888_COLORS);
		}
	}
}