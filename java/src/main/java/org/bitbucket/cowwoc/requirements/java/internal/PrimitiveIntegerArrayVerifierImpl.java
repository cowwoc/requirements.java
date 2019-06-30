/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Default implementation of {@code PrimitiveIntegerArrayVerifier}.
 */
public final class PrimitiveIntegerArrayVerifierImpl
	extends ArrayCapabilitiesImpl<PrimitiveIntegerArrayVerifier, Integer, int[]>
	implements PrimitiveIntegerArrayVerifier
{
	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a view of the array as a collection
	 */
	private static Collection<Integer> asCollection(int[] array)
	{
		if (array == null)
			return null;
		List<Integer> result = new ArrayList<>(array.length);
		for (int element : array)
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
	public PrimitiveIntegerArrayVerifierImpl(ApplicationScope scope, String name, int[] actual,
	                                         Configuration config)
	{
		super(scope, name, actual, asCollection(actual), config);
	}
}
