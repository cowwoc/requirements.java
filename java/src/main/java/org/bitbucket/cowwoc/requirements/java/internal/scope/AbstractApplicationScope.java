/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Reference;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.internal.diff.DiffGenerator;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.JavaSecrets;
import org.bitbucket.cowwoc.requirements.java.internal.util.Exceptions;

/**
 * ApplicationScope for the main and test codebases.
 */
public abstract class AbstractApplicationScope implements ApplicationScope
{
	private final Reference<JavaRequirements> internalVerifier =
		ConcurrentLazyReference.create(() -> JavaSecrets.INSTANCE.createRequirements(this));
	private final Reference<DiffGenerator> diffGenerator =
		ConcurrentLazyReference.create(() -> new DiffGenerator(this));
	private final Exceptions exceptions = new Exceptions();

	@Override
	public DiffGenerator getDiffGenerator()
	{
		return diffGenerator.getValue();
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
}
