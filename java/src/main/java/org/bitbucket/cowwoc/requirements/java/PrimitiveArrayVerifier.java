/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleArrayVerifier;

/**
 * Verifies the requirements of a primitive array.
 *
 * @param <E> the type of elements in the array
 * @param <A> the type of the array
 */
public interface PrimitiveArrayVerifier<E, A>
	extends ExtensibleArrayVerifier<PrimitiveArrayVerifier<E, A>, E, A>
{
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value cannot be null
	 */
	@Override
	@Deprecated
	PrimitiveArrayVerifier<E, A> isNull();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value cannot be null
	 */
	@Override
	@Deprecated
	PrimitiveArrayVerifier<E, A> isNotNull();
}
