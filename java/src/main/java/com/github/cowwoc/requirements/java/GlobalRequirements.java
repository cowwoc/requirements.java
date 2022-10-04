/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;
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
 * <p>
 * <b>Thread-safety</b>: This class is thread-safe.
 */
public final class GlobalRequirements
{
	private static final GlobalConfiguration DELEGATE = MainApplicationScope.INSTANCE.getGlobalConfiguration();

	/**
	 * Prevent construction.
	 */
	private GlobalRequirements()
	{
	}

	/**
	 * Returns the terminal encodings available to the JVM.
	 *
	 * @return the terminal encodings available to the JVM
	 * @see #withTerminalEncoding(TerminalEncoding)
	 * @see #withDefaultTerminalEncoding()
	 */
	public static Set<TerminalEncoding> listTerminalEncodings()
	{
		return DELEGATE.listTerminalEncodings();
	}

	/**
	 * Returns the current terminal encoding.
	 *
	 * @return the current terminal encoding (defaults to the auto-detected encoding)
	 */
	public static TerminalEncoding getTerminalEncoding()
	{
		return DELEGATE.getTerminalEncoding();
	}

	/**
	 * Indicates that the terminal encoding should be auto-detected.
	 *
	 * @return this
	 * @see #withTerminalEncoding(TerminalEncoding)
	 */
	public static GlobalConfiguration withDefaultTerminalEncoding()
	{
		return DELEGATE.withDefaultTerminalEncoding();
	}

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
	public static GlobalConfiguration withTerminalEncoding(TerminalEncoding encoding)
	{
		return DELEGATE.withTerminalEncoding(encoding);
	}

	/**
	 * Indicates if exception stack trace references to this library should be removed, so long as it does not
	 * result in any user code being removed.
	 *
	 * @return {@code true} by default
	 * @see #withCleanStackTrace()
	 * @see #withoutCleanStackTrace()
	 */
	public static boolean isCleanStackTrace()
	{
		return DELEGATE.isCleanStackTrace();
	}

	/**
	 * Indicates that exception stack trace references to this library should be removed, so long as it does
	 * not result in any user code being removed.
	 *
	 * @return this
	 * @see #isCleanStackTrace()
	 */
	public static GlobalConfiguration withCleanStackTrace()
	{
		return DELEGATE.withCleanStackTrace();
	}

	/**
	 * Indicates that exception stack trace references to this library should be kept.
	 *
	 * @return this
	 * @see #isCleanStackTrace()
	 */
	public static GlobalConfiguration withoutCleanStackTrace()
	{
		return DELEGATE.withoutCleanStackTrace();
	}

	/**
	 * Indicates if exceptions should show the difference between the actual and expected values.
	 *
	 * @return true if exceptions should show the difference between the actual and expected values
	 * ({@code true} by default)
	 * @see #withDiff()
	 * @see #withoutDiff()
	 */
	public static boolean isDiffEnabled()
	{
		return DELEGATE.isDiffEnabled();
	}

	/**
	 * Indicates that exceptions should show the difference between the actual and expected values.
	 *
	 * @return this
	 * @see #isDiffEnabled()
	 */
	public static GlobalConfiguration withDiff()
	{
		return DELEGATE.withDiff();
	}

	/**
	 * Indicates that exceptions should not show the difference between the actual and expected values.
	 *
	 * @return this
	 * @see #isDiffEnabled()
	 */
	public static GlobalConfiguration withoutDiff()
	{
		return DELEGATE.withoutDiff();
	}
}