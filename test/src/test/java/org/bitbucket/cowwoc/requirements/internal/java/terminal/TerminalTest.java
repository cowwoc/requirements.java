/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.terminal;

import org.bitbucket.cowwoc.requirements.internal.java.scope.DefaultJvmScope;
import org.bitbucket.cowwoc.requirements.internal.java.scope.JvmScope;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.bitbucket.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;
import static org.bitbucket.cowwoc.requirements.java.terminal.TerminalEncoding.RGB_888COLOR;

public final class TerminalTest
{
	/**
	 * Ensures that none of the native methods fail.
	 *
	 * @throws IOException if an operation fails
	 */
	@Test
	@SuppressWarnings("try")
	public void nativeMethods() throws IOException
	{
		try (JvmScope scope = DefaultJvmScope.INSTANCE)
		{
			NativeTerminal terminal = new NativeTerminal();
			terminal.connect();
			terminal.isConnectedToStdout();
			terminal.setEncoding(NONE);
			terminal.disconnect();
		}
	}

	/**
	 * Force verifies to output using an encoding that might not be supported by the terminal.
	 *
	 * @throws IOException if an operation fails
	 */
	@Test
	@SuppressWarnings("try")
	public void forceUnsupportedEncoding() throws IOException
	{
		try (JvmScope jvm = DefaultJvmScope.INSTANCE)
		{
			Terminal terminal = jvm.getTerminal();
			terminal.setEncoding(RGB_888COLOR);
		}
	}
}
