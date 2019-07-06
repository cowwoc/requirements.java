/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

import org.bitbucket.cowwoc.requirements.java.internal.scope.GlobalConfiguration;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Base class for {@code GlobalConfigurable} instances.
 */
public abstract class AbstractGlobalConfiguration implements GlobalConfiguration
{
	/**
	 * True if exception stack traces should omit references to this library.
	 */
	private final AtomicBoolean cleanStackTrace = new AtomicBoolean(true);
	/**
	 * True if exceptions should contain a diff of the values being compared.
	 */
	private final AtomicBoolean diffEnabled = new AtomicBoolean(true);

	@Override
	public boolean isCleanStackTrace()
	{
		return cleanStackTrace.get();
	}

	@Override
	public GlobalConfiguration withCleanStackTrace()
	{
		cleanStackTrace.set(true);
		return this;
	}

	@Override
	public GlobalConfiguration withoutCleanStackTrace()
	{
		cleanStackTrace.set(false);
		return this;
	}

	@Override
	public boolean isDiffEnabled()
	{
		return diffEnabled.get();
	}

	@Override
	public GlobalConfiguration withDiff()
	{
		diffEnabled.set(true);
		return this;
	}

	@Override
	public GlobalConfiguration withoutDiff()
	{
		diffEnabled.set(false);
		return this;
	}
}
