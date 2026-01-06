/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java;

import java.util.Set;

/**
 * The configuration shared by all validators. Changes apply to existing or new validators.
 * <p>
 * <b>Thread-safety</b>: Implementations must be thread-safe.
 */
public interface GlobalConfiguration
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
	GlobalConfiguration terminalEncoding(TerminalEncoding encoding);
}