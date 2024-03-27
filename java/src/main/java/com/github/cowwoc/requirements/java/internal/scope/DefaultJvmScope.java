/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.internal.terminal.Terminal;

/**
 * The default implementation of JvmScope.
 */
public final class DefaultJvmScope implements JvmScope
{
	/**
	 * The singleton instance.
	 */
	public static final DefaultJvmScope INSTANCE = new DefaultJvmScope();
	private final Terminal terminal = new Terminal();

	private DefaultJvmScope()
	{
	}

	@Override
	public Terminal getTerminal()
	{
		return terminal;
	}

	@Override
	public void close()
	{
	}
}