/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding;

import java.util.Set;

/**
 * The global configuration inherited by all verifiers.
 * <p>
 * <b>Note</b>: Verifiers inherit from the global configuration at instantiation time. Their
 * {@link Configuration local configuration} is not affected by subsequent changes to the global
 * configuration.
 * <p>
 * However, updating settings not found in {@link Configuration} (such as
 * {@link #withTerminalEncoding(TerminalEncoding)}) will impact the behavior of existing verifiers. Users
 * are encouraged to update these settings at startup, or take active measures to ensure that verifiers are
 * idle while the configuration is being updated. Failing to do so may result in undesirable behavior such
 * as verifiers outputting ANSI colors to a terminal that is no longer configured to render them.
 */
public interface GlobalConfigurable
{
	/**
	 * Returns the terminal encodings available to the JVM.
	 *
	 * @return the terminal encodings available to the JVM
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
	GlobalConfigurable withDefaultTerminalEncoding();

	/**
	 * Indicates the type of encoding that verifiers will output.
	 * <p>
	 * This feature can be used to force the use of ANSI colors even when their support is not detected.
	 *
	 * @param encoding the type of encoding that verifiers will output
	 * @return this
	 * @throws NullPointerException if {@code encoding} is null
	 * @see #withDefaultTerminalEncoding()
	 */
	GlobalConfigurable withTerminalEncoding(TerminalEncoding encoding);

	/**
	 * Indicates if exceptions should remove references to this library from their stack trace.
	 *
	 * @return true if exceptions should remove references to this library from their stack trace
	 * ({@code true} by default)
	 * @see #withLibraryRemovedFromStackTrace()
	 * @see #withoutLibraryRemovedFromStackTrace()
	 */
	boolean isLibraryRemovedFromStackTrace();

	/**
	 * Indicates that exceptions should remove references to this library from their stack trace.
	 *
	 * @return this
	 * @see #isLibraryRemovedFromStackTrace()
	 */
	GlobalConfigurable withLibraryRemovedFromStackTrace();

	/**
	 * Indicates that exceptions shouldn't remove references to this library from their stack traces.
	 *
	 * @return this
	 * @see #isLibraryRemovedFromStackTrace()
	 */
	GlobalConfigurable withoutLibraryRemovedFromStackTrace();

	/**
	 * Indicates if exceptions should show the difference between the actual and expected values.
	 *
	 * @return true if exceptions should show the difference between the actual and expected values
	 * ({@code true} by default)
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
	GlobalConfigurable withDiff();

	/**
	 * Indicates that exceptions should not show the difference between the actual and expected values.
	 *
	 * @return this
	 * @see #isDiffEnabled()
	 */
	GlobalConfigurable withoutDiff();
}
