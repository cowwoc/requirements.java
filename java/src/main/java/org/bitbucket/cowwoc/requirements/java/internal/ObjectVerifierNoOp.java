/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

/**
 * An {@code ObjectVerifier} that does nothing.
 *
 * @param <T> the type of the value
 */
public final class ObjectVerifierNoOp<T>
	extends AbstractObjectVerifierNoOp<ObjectVerifier<T>, T>
	implements ObjectVerifier<T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public ObjectVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected ObjectVerifierNoOp<T> getThis()
	{
		return this;
	}
}
