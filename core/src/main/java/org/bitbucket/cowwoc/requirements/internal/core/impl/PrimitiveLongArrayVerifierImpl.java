/*
 * Copyright 2018 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveLongArrayVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * Default implementation of {@link PrimitiveLongArrayVerifier}.
 *
 * @author Gili Tzabari
 */
public class PrimitiveLongArrayVerifierImpl
	extends ArrayCapabilitiesImpl<PrimitiveLongArrayVerifier, Long, long[]>
	implements PrimitiveLongArrayVerifier
{
	/**
	 * @param <E>   the type of elements in the array
	 * @param array an array
	 * @return null if the array is null; otherwise, a view of the array as a collection
	 */
	private static Collection<Long> asCollection(long[] array)
	{
		if (array == null)
			return null;
		List<Long> result = new ArrayList<>(array.length);
		for (long element: array)
			result.add(element);
		return result;
	}

	/**
	 * Creates new ArrayVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	public PrimitiveLongArrayVerifierImpl(ApplicationScope scope, String name, long[] actual,
		Configuration config)
	{
		super(scope, name, actual, asCollection(actual), config);
	}
}
