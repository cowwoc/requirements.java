/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.scope;

import com.github.cowwoc.requirements10.java.internal.terminal.Terminal;

/**
 * The default implementation of ProcessScope.
 */
public final class DefaultProcessScope implements ProcessScope
{
	/**
	 * The singleton instance.
	 */
	public static final DefaultProcessScope INSTANCE = new DefaultProcessScope();
	private final Terminal terminal = new Terminal();

	private DefaultProcessScope()
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