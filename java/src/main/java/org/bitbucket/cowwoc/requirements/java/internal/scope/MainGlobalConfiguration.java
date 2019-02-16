/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

import org.bitbucket.cowwoc.requirements.java.AbstractGlobalConfiguration;
import org.bitbucket.cowwoc.requirements.java.Configuration;
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
 * are encouraged to update these settings at startup, or take active measures to ensure that verifiers
 * are idle while the configuration is being updated. Failing to do so may result in undesirable behavior
 * such as verifiers outputting ANSI colors to a terminal that is no longer configured to render them.
 * <p>
 * This class is thread-safe.
 */
public final class MainGlobalConfiguration extends AbstractGlobalConfiguration
{
	private final JvmScope scope;

	/**
	 * @param scope the system configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public MainGlobalConfiguration(JvmScope scope)
	{
		assert (scope != null);
		this.scope = scope;
	}

	@Override
	public Set<TerminalEncoding> listTerminalEncodings()
	{
		return scope.getTerminal().getSupportedTypes();
	}

	@Override
	public TerminalEncoding getTerminalEncoding()
	{
		return scope.getTerminal().getEncoding();
	}

	@Override
	public GlobalConfiguration withDefaultTerminalEncoding()
	{
		scope.getTerminal().useBestEncoding();
		return this;
	}

	@Override
	public GlobalConfiguration withTerminalEncoding(TerminalEncoding encoding)
	{
		if (encoding == null)
			throw new NullPointerException("encoding may not be null");
		scope.getTerminal().setEncoding(encoding);
		return this;
	}

	@Override
	public String toString()
	{
		return "MainGlobalConfiguration[terminalEncoding=" + getTerminalEncoding() +
			", libraryRemovedFromStackTrace=" + isLibraryRemovedFromStackTrace() +
			", diffEnabled=" + isDiffEnabled() + "]";
	}
}
