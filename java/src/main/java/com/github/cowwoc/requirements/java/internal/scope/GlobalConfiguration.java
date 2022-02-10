/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.natives.terminal.TerminalEncoding;

import java.util.Set;

/**
 * The global configuration inherited by all verifiers.
 * <p>
 * <b>Note</b>: Verifiers inherit from the global configuration at instantiation time. Their
 * {@link Configuration configuration} is not affected by subsequent changes to the global configuration.
 * <p>
 * However, updating settings not found in {@link Configuration} (such as
 * {@link #withTerminalEncoding(TerminalEncoding)}) will impact the behavior of existing verifiers. Users
 * are expected to update these settings at startup, or take active measures to ensure that verifiers are
 * idle while the configuration is being updated. Failure to do so may result in undesirable behavior such
 * as verifiers outputting ANSI colors to a terminal that is no longer configured to render them.
 */
public interface GlobalConfiguration
{
	/**
	 * Returns the encodings supported by the terminal.
	 *
	 * @return the encodings supported by the terminal
	 * @see #withTerminalEncoding(TerminalEncoding)
	 * @see #withDefaultTerminalEncoding()
	 */
	Set<TerminalEncoding> listTerminalEncodings();

	/**
	 * Returns the current terminal encoding.
	 *
	 * @return the current terminal encoding (defaults to the auto-detected encoding)
	 */
	TerminalEncoding getTerminalEncoding();

	/**
	 * Indicates that the terminal encoding should be auto-detected.
	 *
	 * @return this
	 * @see #withTerminalEncoding(TerminalEncoding)
	 */
	GlobalConfiguration withDefaultTerminalEncoding();

	/**
	 * Indicates the type of encoding that the terminal supports.
	 * <p>
	 * This feature can be used to force the use of ANSI colors even when their support is not detected.
	 *
	 * @param encoding the type of encoding that the terminal supports
	 * @return this
	 * @throws NullPointerException if {@code encoding} is null
	 * @see #withDefaultTerminalEncoding()
	 */
	GlobalConfiguration withTerminalEncoding(TerminalEncoding encoding);

	/**
	 * Returns the width of the terminal.
	 *
	 * @return the width of the terminal in characters (defaults to the auto-detected width)
	 */
	int getTerminalWidth();

	/**
	 * Indicates that the terminal width should be auto-detected. If the width cannot be auto-detected, a value
	 * of {@code 80} is used.
	 *
	 * @return this
	 * @see #withTerminalWidth(int)
	 */
	GlobalConfiguration withDefaultTerminalWidth();

	/**
	 * Indicates the width of the terminal.
	 * <p>
	 * This feature can be used to override the default terminal width when it cannot be auto-detected.
	 *
	 * @param width the width of the terminal in characters
	 * @return this
	 * @throws IllegalArgumentException if {@code width} is zero or negative
	 * @see #withDefaultTerminalWidth()
	 */
	GlobalConfiguration withTerminalWidth(int width);

	/**
	 * Indicates if stack trace references to this library should be removed.
	 *
	 * @return {@code true} by default
	 * @see #withCleanStackTrace()
	 * @see #withoutCleanStackTrace()
	 */
	boolean isCleanStackTrace();

	/**
	 * Indicates that stack trace references to this library should be removed.
	 *
	 * @return this
	 * @see #isCleanStackTrace()
	 */
	GlobalConfiguration withCleanStackTrace();

	/**
	 * Indicates that stack trace references to this library should be kept.
	 *
	 * @return this
	 * @see #isCleanStackTrace()
	 */
	GlobalConfiguration withoutCleanStackTrace();

	/**
	 * Indicates if exceptions should show the difference between the actual and expected values.
	 *
	 * @return {@code true} by default
	 * @see #withDiff()
	 * @see #withoutDiff()
	 */
	boolean isDiffEnabled();

	/**
	 * Indicates that exceptions should show the difference between the actual and expected values.
	 *
	 * @return this
	 * @see #isDiffEnabled()
	 */
	GlobalConfiguration withDiff();

	/**
	 * Indicates that exceptions should not show the difference between the actual and expected values.
	 *
	 * @return this
	 * @see #isDiffEnabled()
	 */
	GlobalConfiguration withoutDiff();
}
