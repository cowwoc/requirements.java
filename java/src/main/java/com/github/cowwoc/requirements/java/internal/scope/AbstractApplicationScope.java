/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.pouch.core.ConcurrentLazyReference;
import com.github.cowwoc.pouch.core.Reference;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.internal.secrets.JavaSecrets;
import com.github.cowwoc.requirements.java.internal.util.Exceptions;

/**
 * ApplicationScope for the main and test codebases.
 */
public abstract class AbstractApplicationScope implements ApplicationScope
{
	// withoutCleanStacktrace() because the error occurred in our API, not the user's API.
	private final Reference<JavaRequirements> internalVerifier =
		ConcurrentLazyReference.create(() ->
			JavaSecrets.INSTANCE.createRequirements(this).withoutCleanStackTrace());
	private final Exceptions exceptions = new Exceptions(this);

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
