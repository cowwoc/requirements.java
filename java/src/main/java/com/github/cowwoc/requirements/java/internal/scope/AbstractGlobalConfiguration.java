/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.GlobalConfiguration;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Base class for {@code GlobalConfiguration} instances.
 */
public abstract class AbstractGlobalConfiguration implements GlobalConfiguration
{
	/**
	 * True if stack trace references to this library should be removed, so long as it does not result in any
	 * user code being removed.
	 */
	private final AtomicBoolean cleanStackTrace = new AtomicBoolean(true);
	/**
	 * True if exceptions should contain a diff of the values being compared.
	 */
	private final AtomicBoolean diffEnabled = new AtomicBoolean(true);

	protected AbstractGlobalConfiguration()
	{}

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