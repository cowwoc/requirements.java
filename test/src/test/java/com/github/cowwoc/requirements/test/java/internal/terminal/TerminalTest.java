/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java.internal.terminal;

import com.github.cowwoc.requirements.java.internal.scope.DefaultJvmScope;
import com.github.cowwoc.requirements.java.internal.scope.JvmScope;
import com.github.cowwoc.requirements.java.internal.terminal.Terminal;
import com.github.cowwoc.requirements.natives.internal.terminal.NativeTerminal;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.RGB_888_COLORS;

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
		try (JvmScope ignored = DefaultJvmScope.INSTANCE)
		{
			NativeTerminal terminal = new NativeTerminal();
			terminal.connect();
			terminal.isConnectedToStdout();
			terminal.setEncoding(NONE);
			terminal.disconnect();
		}
	}

	/**
	 * Force verifiers to output using an encoding that might not be supported by the terminal.
	 */
	@Test
	public void forceUnsupportedEncoding()
	{
		try (DefaultJvmScope jvm = DefaultJvmScope.INSTANCE)
		{
			Terminal terminal = jvm.getTerminal();
			terminal.setEncoding(RGB_888_COLORS);
		}
	}
}
