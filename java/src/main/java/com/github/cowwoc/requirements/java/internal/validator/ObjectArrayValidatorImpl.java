/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.validator;

import com.github.cowwoc.requirements.java.internal.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.validator.ObjectArrayValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @param <E> the type of elements in the array
 */
public final class ObjectArrayValidatorImpl<E> extends AbstractArrayValidator<ObjectArrayValidator<E[], E>, E[], E>
	implements ObjectArrayValidator<E[], E>
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	public ObjectArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<E[]> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(E[] array)
	{
		return array.length;
	}

	@Override
	protected List<E> asList(E[] array)
	{
		return java.util.Arrays.asList(array);
	}

	@Override
	protected boolean contains(E[] array, E element)
	{
		return Arrays.contains(array, element);
	}

	@Override
	protected Set<E> getDuplicates(E[] value)
	{
		Set<E> unique = new HashSet<>(value.length);
		Set<E> duplicates = new HashSet<>(value.length);
		for (E element : value)
		{
			if (!unique.add(element))
				duplicates.add(element);
		}
		return duplicates;
	}

	@Override
	public ObjectArrayValidator<E[], E> containsSameNullity()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value ->
		{
			int numberOfNulls = 0;
			for (E element : value)
				if (element == null)
					++numberOfNulls;
			return numberOfNulls == getLength(value);
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsSameNullity(scope, this).toString());
		}
		return this;
	}
}