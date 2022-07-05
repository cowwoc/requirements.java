/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.ThreadConfiguration;

import java.util.function.Supplier;

/**
 * ApplicationScope for the main codebase.
 */
public final class MainApplicationScope extends AbstractApplicationScope
{
	/**
	 * The singleton instance.
	 */
	public static final MainApplicationScope INSTANCE = new MainApplicationScope();
	/**
	 * The global configuration.
	 */
	private final GlobalConfiguration globalConfiguration;
	private final ThreadLocal<ThreadConfiguration> threadConfiguration =
		ThreadLocal.withInitial(() -> new DefaultThreadConfiguration(this));

	/**
	 * Creates a new application scope.
	 */
	public MainApplicationScope()
	{
		this.globalConfiguration = new MainGlobalConfiguration(parent.getTerminal());
	}

	@Override
	public Supplier<ThreadConfiguration> getThreadConfiguration()
	{
		return threadConfiguration::get;
	}

	@Override
	public GlobalConfiguration getGlobalConfiguration()
	{
		return globalConfiguration;
	}

	@Override
	public void close()
	{
	}
}
