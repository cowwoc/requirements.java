/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.pouch.core.LazyReference;
import com.github.cowwoc.pouch.core.Reference;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.GlobalConfiguration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.ThreadConfiguration;
import com.github.cowwoc.requirements.java.internal.secrets.JavaSecrets;
import com.github.cowwoc.requirements.java.internal.terminal.Terminal;
import com.github.cowwoc.requirements.java.internal.util.Exceptions;

import java.util.function.Supplier;

/**
 * ApplicationScope for the main and test codebases.
 */
public abstract class AbstractApplicationScope implements ApplicationScope
{
	protected final JvmScope parent = DefaultJvmScope.INSTANCE;
	// withoutCleanStacktrace() because this verifier is used to catch errors in our API, not just the user's
	// code.
	private final Reference<JavaRequirements> internalVerifier = LazyReference.create(() ->
		JavaSecrets.INSTANCE.createRequirements(this).withoutCleanStackTrace());
	private final Supplier<Configuration> defaultConfiguration;
	private final ThreadLocal<ThreadConfiguration> threadConfiguration =
		ThreadLocal.withInitial(DefaultThreadConfiguration::new);
	private final Exceptions exceptions = new Exceptions();

	/**
	 * Creates a new instance.
	 */
	protected AbstractApplicationScope()
	{
		this.defaultConfiguration = () ->
		{
			GlobalConfiguration globalConfiguration = getGlobalConfiguration();
			Configuration result = new DefaultConfiguration();
			if (!getGlobalConfiguration().isCleanStackTrace())
				result.withoutCleanStackTrace();
			if (!globalConfiguration.isDiffEnabled())
				result.withoutDiff();
			return result;
		};
	}

	@Override
	public Terminal getTerminal()
	{
		return parent.getTerminal();
	}

	@Override
	public Supplier<Configuration> getDefaultConfiguration()
	{
		return defaultConfiguration;
	}

	@Override
	public Exceptions getExceptions()
	{
		return exceptions;
	}

	@Override
	public JavaRequirements getInternalVerifier()
	{
		return internalVerifier.getValue();
	}

	@Override
	public Supplier<ThreadConfiguration> getThreadConfiguration()
	{
		return threadConfiguration::get;
	}
}