/*
 * Copyright 2018 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.impl;

import org.bitbucket.cowwoc.requirements.internal.java.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveDoubleArrayVerifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Default implementation of {@link PrimitiveDoubleArrayVerifier}.
 */
public class PrimitiveDoubleArrayVerifierImpl
	extends ArrayCapabilitiesImpl<PrimitiveDoubleArrayVerifier, Double, double[]>
	implements PrimitiveDoubleArrayVerifier
{
	/**
	 * @param <E>   the type of elements in the array
	 * @param array an array
	 * @return null if the array is null; otherwise, a view of the array as a collection
	 */
	private static Collection<Double> asCollection(double[] array)
	{
		if (array == null)
			return null;
		List<Double> result = new ArrayList<>(array.length);
		for (double element : array)
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
	public PrimitiveDoubleArrayVerifierImpl(ApplicationScope scope, String name, double[] actual, Configuration config)
	{
		super(scope, name, actual, asCollection(actual), config);
	}
}
