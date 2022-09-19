/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.natives.internal.terminal;

import com.github.cowwoc.requirements.natives.terminal.TerminalEncoding;

import java.io.IOException;

/**
 * Native functions for interacting with the JVM terminal.
 */
// WORKAROUND: https://youtrack.jetbrains.com/issue/IDEA-205525
@SuppressWarnings("RedundantThrows")
public final class NativeTerminal
{
	/**
	 * Creates a new NativeTerminal.
	 */
	public NativeTerminal()
	{
	}

	/**
	 * Connects to the terminal.
	 *
	 * @throws IOException if the operation fails
	 */
	public native void connect() throws IOException;

	/**
	 * @return true if stdout is connected to the terminal
	 * @throws IOException if the operation fails
	 */
	public native boolean isConnectedToStdout() throws IOException;

	/**
	 * Enables ANSI escape codes.
	 * <p>
	 * Most terminals support ANSI escape codes by default, but some platforms (e.g. Windows) require
	 * support to be enabled.
	 *
	 * @param encoding the type of encoding that the terminal supports
	 * @throws IOException if the operation fails
	 */
	public native void setEncoding(TerminalEncoding encoding) throws IOException;

	/**
	 * Returns the width of the terminal.
	 *
	 * @return the width in characters
	 * @throws IOException if the operation fails
	 */
	public native int getWidth() throws IOException;

	/**
	 * Disconnects from the terminal.
	 * <p>
	 * Does nothing if the terminal is already disconnected.
	 *
	 * @throws IOException if the operation fails
	 */
	public native void disconnect() throws IOException;
}
