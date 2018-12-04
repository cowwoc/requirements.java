/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.terminal;

import org.bitbucket.cowwoc.requirements.java.terminal.TerminalEncoding;

import java.io.IOException;

/**
 * Native functions for interacting with the JVM terminal.
 */
public final class NativeTerminal
{
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
	 * @param encoding the type of encoding that verifiers will output
	 * @throws IOException if the operation fails
	 */
	public native void setEncoding(TerminalEncoding encoding) throws IOException;

	/**
	 * Disconnects from the terminal.
	 * <p>
	 * Does nothing if the terminal is already disconnected.
	 *
	 * @throws IOException if the operation fails
	 */
	public native void disconnect() throws IOException;
}
