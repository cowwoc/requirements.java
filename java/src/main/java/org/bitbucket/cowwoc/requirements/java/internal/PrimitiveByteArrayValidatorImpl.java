/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveByteArrayValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Default implementation of {@code PrimitiveByteArrayValidator}.
 */
public class PrimitiveByteArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveByteArrayValidator, Byte, byte[]>
	implements PrimitiveByteArrayValidator
{
	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a collection containing the array elements
	 */
	private static Collection<Byte> asCollection(byte[] array)
	{
		if (array == null)
			return null;
		List<Byte> result = new ArrayList<>(array.length);
		for (byte element : array)
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
	public PrimitiveByteArrayValidatorImpl(ApplicationScope scope, Configuration config, String name,
	                                       byte[] actual, List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, asCollection(actual), failures);
	}

	@Override
	protected PrimitiveByteArrayValidator getThis()
	{
		return this;
	}

	@Override
	protected PrimitiveByteArrayValidator getNoOp()
	{
		return new PrimitiveByteArrayValidatorNoOp(scope, config, failures);
	}
}
