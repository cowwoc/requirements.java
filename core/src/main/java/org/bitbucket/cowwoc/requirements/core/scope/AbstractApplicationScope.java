/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.scope;

import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Reference;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.CoreRequirements;
import org.bitbucket.cowwoc.requirements.core.CoreVerifiers;
import org.bitbucket.cowwoc.requirements.core.diff.DiffGenerator;

/**
 * ApplicationScope for the main and test codebases.
 *
 * @author Gili Tzabari
 */
public abstract class AbstractApplicationScope implements ApplicationScope
{
	private final Configuration defaultConfiguration = new Configuration();
	private final Reference<CoreVerifiers> internalVerifier = ConcurrentLazyReference.create(() ->
		new CoreVerifiers(this, CoreRequirements.assertionsAreEnabled()));
	private final Reference<DiffGenerator> diffGenerator = ConcurrentLazyReference.create(() ->
		new DiffGenerator(this));

	@Override
	public DiffGenerator getDiffGenerator()
	{
		return diffGenerator.getValue();
	}

	@Override
	public CoreVerifiers getInternalVerifier()
	{
		return internalVerifier.getValue();
	}

	@Override
	public Configuration getDefaultConfiguration()
	{
		return defaultConfiguration;
	}
}
