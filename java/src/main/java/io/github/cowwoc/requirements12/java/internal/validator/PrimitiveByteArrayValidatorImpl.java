/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.internal.validator;

import io.github.cowwoc.requirements12.java.ValidationFailure;
import io.github.cowwoc.requirements12.java.internal.Configuration;
import io.github.cowwoc.requirements12.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements12.java.internal.util.Arrays;
import io.github.cowwoc.requirements12.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements12.java.validator.PrimitiveByteArrayValidator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class PrimitiveByteArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveByteArrayValidator, byte[], Byte>
	implements PrimitiveByteArrayValidator
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value being validated
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public PrimitiveByteArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<byte[]> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(byte[] array)
	{
		return array.length;
	}

	@Override
	protected List<Byte> asList(byte[] array)
	{
		return Arrays.asList(array);
	}

	@Override
	protected boolean contains(byte[] array, Byte element)
	{
		return Arrays.contains(array, element);
	}

	@Override
	protected Set<Byte> getDuplicates(byte[] value)
	{
		Set<Byte> unique = new HashSet<>(value.length);
		Set<Byte> duplicates = new HashSet<>(value.length);
		for (byte element : value)
		{
			if (!unique.add(element))
				duplicates.add(element);
		}
		return duplicates;
	}

	@Override
	public PrimitiveByteArrayValidator contains(byte expected)
	{
		return contains((Byte) expected);
	}

	@Override
	public PrimitiveByteArrayValidator contains(byte expected, String name)
	{
		return contains((Byte) expected, name);
	}

	@Override
	public PrimitiveByteArrayValidator doesNotContain(byte unwanted)
	{
		return doesNotContain((Byte) unwanted);
	}

	@Override
	public PrimitiveByteArrayValidator doesNotContain(byte unwanted, String name)
	{
		return doesNotContain((Byte) unwanted, name);
	}

	@Override
	public PrimitiveByteArrayValidator isSorted()
	{
		return isSorted(Comparator.naturalOrder());
	}
}