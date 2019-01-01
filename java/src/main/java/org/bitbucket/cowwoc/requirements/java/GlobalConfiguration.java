/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.internal.scope.JvmScope;
import org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The global configuration inherited by all verifiers.
 * <p>
 * <b>Note</b>: Verifiers inherit from the global configuration at instantiation time. Their {@link Configuration local configuration} is
 * not affected by subsequent changes to the global configuration.
 * <p>
 * However, updating settings not found in {@link Configuration} (such as {@link #withTerminalEncoding(TerminalEncoding)}) will impact
 * the behavior of existing verifiers. Users are encouraged to update these settings at startup, or take active measures to ensure that
 * verifiers are idle while the configuration is being updated. Failing to do so may result in undesirable behavior such as verifiers
 * outputting ANSI colors to a terminal that is no longer configured to render them.
 * <p>
 * This class is thread-safe.
 */
public final class GlobalConfiguration
{
	private final JvmScope scope;
	/**
	 * True if API elements should show up in exception stacktraces.
	 */
	private final AtomicBoolean removeLibraryFromStacktrace = new AtomicBoolean(true);
	/**
	 * True if exceptions should contain a diff of the values being compared.
	 */
	private final AtomicBoolean diffEnabled = new AtomicBoolean(true);

	/**
	 * @param scope the system configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public GlobalConfiguration(JvmScope scope)
	{
		assert (scope != null);
		this.scope = scope;
	}

	/**
	 * @return the terminal encodings available to the JVM
	 * @see #withTerminalEncoding(TerminalEncoding)
	 * @see #withDefaultTerminalEncoding()
	 */
	public Set<TerminalEncoding> listTerminalEncodings()
	{
		return scope.getTerminal().getSupportedTypes();
	}

	/**
	 * @return the current terminal encoding (defaults to the auto-detected encoding)
	 */
	public TerminalEncoding getTerminalEncoding()
	{
		return scope.getTerminal().getEncoding();
	}

	/**
	 * Indicates that the terminal encoding should be auto-detected.
	 *
	 * @return this
	 * @see #withTerminalEncoding(TerminalEncoding)
	 */
	public GlobalConfiguration withDefaultTerminalEncoding()
	{
		scope.getTerminal().useBestEncoding();
		return this;
	}

	/**
	 * Indicates the type of encoding that verifiers will output.
	 * <p>
	 * This feature can be used to force the use of ANSI colors even when their support is not
	 * detected.
	 *
	 * @param encoding the type of encoding that verifiers will output
	 * @return this
	 * @throws NullPointerException if {@code encoding} is null
	 * @see #withDefaultTerminalEncoding()
	 */
	public GlobalConfiguration withTerminalEncoding(TerminalEncoding encoding)
	{
		if (encoding == null)
			throw new NullPointerException("encoding may not be null");
		scope.getTerminal().setEncoding(encoding);
		return this;
	}

	/**
	 * @return true if exceptions should remove references to this library from their stack traces ({@code true} by default)
	 * @see #withLibraryRemovedFromStacktrace()
	 * @see #withoutLibraryRemovedFromStacktrace()
	 */
	public boolean isLibraryRemovedFromStacktrace()
	{
		return removeLibraryFromStacktrace.get();
	}

	/**
	 * Indicates that exceptions should remove references to this library from their stack traces.
	 *
	 * @return this
	 * @see #isLibraryRemovedFromStacktrace()
	 */
	public GlobalConfiguration withLibraryRemovedFromStacktrace()
	{
		removeLibraryFromStacktrace.set(true);
		return this;
	}

	/**
	 * Indicates that exceptions shouldn't remove references to this library from their stack traces.
	 *
	 * @return this
	 * @see #isLibraryRemovedFromStacktrace()
	 */
	public GlobalConfiguration withoutLibraryRemovedFromStacktrace()
	{
		removeLibraryFromStacktrace.set(false);
		return this;
	}

	/**
	 * @return true if exceptions should show the difference between the actual and expected values ({@code true} by default)
	 * @see #withDiff()
	 * @see #withoutDiff()
	 */
	public boolean isDiffEnabled()
	{
		return diffEnabled.get();
	}

	/**
	 * Indicates that exceptions should show the difference between the actual and expected values.
	 *
	 * @return this
	 * @see #isDiffEnabled()
	 */
	public GlobalConfiguration withDiff()
	{
		diffEnabled.set(true);
		return this;
	}

	/**
	 * Indicates that exceptions should not show the difference between the actual and expected values.
	 *
	 * @return this
	 * @see #isDiffEnabled()
	 */
	public GlobalConfiguration withoutDiff()
	{
		diffEnabled.set(false);
		return this;
	}
}
