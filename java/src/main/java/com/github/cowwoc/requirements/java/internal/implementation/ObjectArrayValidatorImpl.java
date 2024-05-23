/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ComparableMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.type.ObjectArrayValidator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @param <E> the type of elements in the array
 */
public final class ObjectArrayValidatorImpl<E> extends AbstractArrayValidator<ObjectArrayValidator<E, E[]>, E, E[]>
	implements ObjectArrayValidator<E, E[]>
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public ObjectArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name, E[] value,
		Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(E[] array)
	{
		return array.length;
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
	protected boolean isSorted(E[] array, Comparator<E> comparator)
	{
		return Arrays.isSorted(array, comparator);
	}

	@Override
	protected List<E> sorted(E[] array, Comparator<E> comparator)
	{
		List<E> list = Arrays.asList(array);
		list.sort(comparator);
		return list;
	}

	@Override
	public ObjectArrayValidator<E, E[]> containsSameNullity()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.containsSameNullity(scope, this, this.name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else
		{
			int numberOfNulls = 0;
			for (E element : value)
				if (element == null)
					++numberOfNulls;
			if (numberOfNulls != getLength(value))
			{
				addIllegalArgumentException(
					CollectionMessages.containsSameNullity(scope, this, this.name, value).toString());
			}
		}
		return this;
	}

	@Override
	public ObjectArrayValidator<E, E[]> contains(E expected)
	{
		return containsImpl(expected, null);
	}

	@Override
	public ObjectArrayValidator<E, E[]> contains(E expected, String name)
	{
		requireThatNameIsUnique(name);
		return containsImpl(expected, name);
	}

	private ObjectArrayValidator<E, E[]> containsImpl(E expected, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, this.name, null, "must contain", name,
					expected).toString());
		}
		else if (expected == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!Arrays.contains(value, expected))
		{
			MessageBuilder messageBuilder = ComparableMessages.getExpectedVsActual(scope, this, this.name,
					value, "must contain", name, expected).
				withContext(value, this.name);
			if (name != null)
				messageBuilder.withContext(expected, name);
			addIllegalArgumentException(messageBuilder.toString());
		}
		return this;
	}

	@Override
	public ObjectArrayValidator<E, E[]> doesNotContain(E unwanted)
	{
		return doesNotContainImpl(unwanted, null);
	}

	@Override
	public ObjectArrayValidator<E, E[]> doesNotContain(E unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainImpl(unwanted, name);
	}

	private ObjectArrayValidator<E, E[]> doesNotContainImpl(E unwanted, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContain(scope, this, this.name, null, null, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (getValueAsSet().contains(unwanted))
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContain(scope, this, this.name, value, name, unwanted).toString());
		}
		return this;
	}
}