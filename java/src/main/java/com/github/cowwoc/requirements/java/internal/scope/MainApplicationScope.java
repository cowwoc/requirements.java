/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

/**
 * ApplicationScope for the main codebase.
 */
public final class MainApplicationScope extends AbstractApplicationScope
{
	/**
	 * The singleton instance.
	 */
	public static final MainApplicationScope INSTANCE = new MainApplicationScope(DefaultProcessScope.INSTANCE);

	/**
	 * Creates a new application scope.
	 *
	 * @param parent the parent scope
	 * @throws NullPointerException if {@code parent} is null
	 */
	private MainApplicationScope(ProcessScope parent)
	{
		super(parent, new MainGlobalConfiguration(parent.getTerminal()));
	}

	@Override
	public void close()
	{
	}
}