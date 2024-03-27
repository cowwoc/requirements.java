/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.terminal.TerminalEncoding;

import java.util.Set;

/**
 * Updates the global configuration shared by all validators. Changes apply to validations started after an
 * update.
 */
public interface UpdatableGlobalConfiguration extends AutoCloseable
{
	/**
	 * Returns the encoding supported by the terminal.
	 *
	 * @return the encoding supported by the terminal
	 */
	Set<TerminalEncoding> supportedTerminalEncodings();

	/**
	 * Returns the current terminal encoding.
	 *
	 * @return the current terminal encoding (defaults to the auto-detected encoding)
	 */
	TerminalEncoding terminalEncoding();

	/**
	 * Sets the terminal encoding of the output.
	 * <p>
	 * This can be used to force the use of ANSI colors when their support is not detected.
	 *
	 * @param encoding the type of encoding that the terminal supports
	 * @return this
	 * @throws NullPointerException if {@code encoding} is null
	 */
	UpdatableGlobalConfiguration terminalEncoding(TerminalEncoding encoding);

	@Override
	void close();
}