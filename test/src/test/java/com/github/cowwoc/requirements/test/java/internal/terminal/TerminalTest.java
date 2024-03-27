/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java.internal.terminal;

import com.github.cowwoc.requirements.java.internal.scope.DefaultJvmScope;
import com.github.cowwoc.requirements.java.internal.terminal.Terminal;
import com.github.cowwoc.requirements.java.terminal.TerminalEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public final class TerminalTest
{
	/**
	 * Force validators to output using an encoding that might not be supported by the terminal.
	 */
	@Test
	public void forceUnsupportedEncoding()
	{
		try (DefaultJvmScope jvm = DefaultJvmScope.INSTANCE)
		{
			Terminal terminal = jvm.getTerminal();

			Logger log = LoggerFactory.getLogger(TerminalTest.class);
			log.debug("*** The following exception is expected and does not denote a test failure ***");
			terminal.setEncoding(TerminalEncoding.RGB_888_COLORS);
		}
	}
}