/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.pouch.core.ConcurrentLazyReference;
import com.github.cowwoc.pouch.core.Reference;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.GlobalConfiguration;
import com.github.cowwoc.requirements.java.internal.implementation.JavaValidatorsImpl;
import com.github.cowwoc.requirements.java.internal.implementation.MutableConfiguration;
import com.github.cowwoc.requirements.java.internal.terminal.Terminal;

/**
 * ApplicationScope for the main and test codebases.
 */
public abstract class AbstractApplicationScope implements ApplicationScope
{
	private final JvmScope parent;
	/**
	 * The global configuration.
	 */
	private final GlobalConfiguration globalConfiguration;
	@SuppressWarnings("this-escape")
	private final Reference<JavaValidatorsImpl> internalValidator = ConcurrentLazyReference.create(() ->
		new JavaValidatorsImpl(this, MutableConfiguration.from(Configuration.DEFAULT).cleanStackTrace(false).
			toImmutable()));

	/**
	 * Creates a new instance.
	 *
	 * @param parent              the parent scope
	 * @param globalConfiguration the global configuration
	 * @throws NullPointerException if any of the arguments are null
	 */
	protected AbstractApplicationScope(JvmScope parent, GlobalConfiguration globalConfiguration)
	{
		assert (parent != null);
		assert (globalConfiguration != null);
		this.parent = parent;
		this.globalConfiguration = globalConfiguration;
	}

	@Override
	public GlobalConfiguration getGlobalConfiguration()
	{
		return globalConfiguration;
	}

	@Override
	public Terminal getTerminal()
	{
		return parent.getTerminal();
	}

	@Override
	public JavaValidatorsImpl getInternalValidator()
	{
		return internalValidator.getValue();
	}
}