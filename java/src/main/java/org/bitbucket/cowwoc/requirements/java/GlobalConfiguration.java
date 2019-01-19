/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.internal.scope.JvmScope;
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
public final class GlobalConfiguration extends AbstractGlobalConfigurable
{
	private final JvmScope scope;

	/**
	 * @param scope the system configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public GlobalConfiguration(JvmScope scope)
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
	public GlobalConfigurable withDefaultTerminalEncoding()
	{
		scope.getTerminal().useBestEncoding();
		return this;
	}

	@Override
	public GlobalConfigurable withTerminalEncoding(TerminalEncoding encoding)
	{
		if (encoding == null)
			throw new NullPointerException("encoding may not be null");
		scope.getTerminal().setEncoding(encoding);
		return this;
	}
}
