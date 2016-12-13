/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.scope;

import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Reference;
import org.bitbucket.cowwoc.requirements.core.diff.DiffGenerator;
import org.bitbucket.cowwoc.requirements.core.diff.Terminal;

/**
 * SingletonScope for the main and test codebases.
 *
 * @author Gili Tzabari
 */
public abstract class AbstractSingletonScope implements SingletonScope
{
	private final Reference<Terminal> terminal = ConcurrentLazyReference.create(() ->
		new Terminal(this));
	private final Reference<DiffGenerator> diffGenerator = ConcurrentLazyReference.create(() ->
		new DiffGenerator(this));

	@Override
	public Terminal getTerminal()
	{
		return terminal.getValue();
	}

	@Override
	public DiffGenerator getDiffGenerator()
	{
		return diffGenerator.getValue();
	}
}