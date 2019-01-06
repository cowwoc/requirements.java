/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Base class for {@code GlobalConfigurable} instances.
 */
public abstract class AbstractGlobalConfigurable implements GlobalConfigurable
{
	/**
	 * True if exceptions should remove references to this library from their stack traces.
	 */
	private final AtomicBoolean removeLibraryFromStackTrace = new AtomicBoolean(true);
	/**
	 * True if exceptions should contain a diff of the values being compared.
	 */
	private final AtomicBoolean diffEnabled = new AtomicBoolean(true);

	@Override
	public boolean isLibraryRemovedFromStackTrace()
	{
		return removeLibraryFromStackTrace.get();
	}

	@Override
	public GlobalConfigurable withLibraryRemovedFromStackTrace()
	{
		removeLibraryFromStackTrace.set(true);
		return this;
	}

	@Override
	public GlobalConfigurable withoutLibraryRemovedFromStackTrace()
	{
		removeLibraryFromStackTrace.set(false);
		return this;
	}

	@Override
	public boolean isDiffEnabled()
	{
		return diffEnabled.get();
	}

	@Override
	public GlobalConfigurable withDiff()
	{
		diffEnabled.set(true);
		return this;
	}

	@Override
	public GlobalConfigurable withoutDiff()
	{
		diffEnabled.set(false);
		return this;
	}
}
