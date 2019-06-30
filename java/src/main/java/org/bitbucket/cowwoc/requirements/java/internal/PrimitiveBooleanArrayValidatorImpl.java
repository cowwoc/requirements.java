/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanArrayValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Default implementation of {@code PrimitiveBooleanArrayValidator}.
 */
public class PrimitiveBooleanArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveBooleanArrayValidator, Boolean, boolean[]>
	implements PrimitiveBooleanArrayValidator
{
	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a view of the array as a collection
	 */
	private static Collection<Boolean> asCollection(boolean[] array)
	{
		if (array == null)
			return null;
		List<Boolean> result = new ArrayList<>(array.length);
		for (boolean element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param scope    the application configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code name}, {@code config} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public PrimitiveBooleanArrayValidatorImpl(ApplicationScope scope, String name, boolean[] actual,
	                                          Configuration config, List<ValidationFailure> failures)
	{
		super(scope, name, actual, asCollection(actual), config, failures);
	}
}
