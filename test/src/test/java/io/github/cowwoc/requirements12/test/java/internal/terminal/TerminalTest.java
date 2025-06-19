/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.test.java.internal.terminal;

import io.github.cowwoc.requirements12.java.TerminalEncoding;
import io.github.cowwoc.requirements12.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements12.java.internal.terminal.Terminal;
import io.github.cowwoc.requirements12.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import static io.github.cowwoc.requirements12.java.TerminalEncoding.NONE;

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