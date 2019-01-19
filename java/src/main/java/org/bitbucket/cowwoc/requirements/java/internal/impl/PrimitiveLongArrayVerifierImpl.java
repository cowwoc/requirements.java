/*
 * Copyright (c) 2018 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveLongArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Default implementation of {@link PrimitiveLongArrayVerifier}.
 */
public class PrimitiveLongArrayVerifierImpl
	extends ArrayCapabilitiesImpl<PrimitiveLongArrayVerifier, Long, long[]>
	implements PrimitiveLongArrayVerifier
{
	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a view of the array as a collection
	 */
	private static Collection<Long> asCollection(long[] array)
	{
		if (array == null)
			return null;
		List<Long> result = new ArrayList<>(array.length);
		for (long element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code name} or {@code config} are null. If {@code name} is empty.
	 */
	protected PrimitiveLongArrayVerifierImpl(ApplicationScope scope, String name, long[] actual,
	                                         Configuration config)
	{
		super(scope, name, actual, asCollection(actual), config);
	}
}
