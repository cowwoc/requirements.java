/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveShortArrayValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Default implementation of {@code PrimitiveShortArrayValidator}.
 */
public class PrimitiveShortArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveShortArrayValidator, Short, short[]>
	implements PrimitiveShortArrayValidator
{
	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a collection containing the array elements
	 */
	private static Collection<Short> asCollection(short[] array)
	{
		if (array == null)
			return null;
		List<Short> result = new ArrayList<>(array.length);
		for (short element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public PrimitiveShortArrayValidatorImpl(ApplicationScope scope, Configuration config, String name,
	                                        short[] actual, List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, asCollection(actual), failures);
	}

	@Override
	protected PrimitiveShortArrayValidator getThis()
	{
		return this;
	}

	@Override
	protected PrimitiveShortArrayValidator getNoOp()
	{
		return new PrimitiveShortArrayValidatorNoOp(scope, config, failures);
	}
}
