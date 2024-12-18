/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java.internal.terminal;

import com.github.cowwoc.requirements10.java.internal.scope.DefaultProcessScope;
import com.github.cowwoc.requirements10.java.internal.terminal.Terminal;
import com.github.cowwoc.requirements10.java.TerminalEncoding;
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
		try (DefaultProcessScope process = DefaultProcessScope.INSTANCE)
		{
			Terminal terminal = process.getTerminal();

			Logger log = LoggerFactory.getLogger(TerminalTest.class);
			log.debug("*** The following exception is expected and does not denote a test failure ***");
			terminal.setEncoding(TerminalEncoding.RGB_888_COLORS);
		}
	}
}