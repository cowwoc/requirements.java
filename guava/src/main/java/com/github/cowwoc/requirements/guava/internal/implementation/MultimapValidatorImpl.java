/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.guava.internal.implementation;

import com.github.cowwoc.requirements.guava.MultimapValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.AbstractObjectValidator;
import com.github.cowwoc.requirements.java.internal.implementation.CollectionValidatorImpl;
import com.github.cowwoc.requirements.java.internal.implementation.PrimitiveUnsignedIntegerValidatorImpl;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.type.CollectionValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveUnsignedIntegerValidator;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @param <K> the type of keys in the {@code Multimap}
 * @param <V> the type of values in the {@code Multimap}
 */
public final class MultimapValidatorImpl<K, V, T extends Multimap<K, V>>
	extends AbstractObjectValidator<MultimapValidator<K, V, T>, T>
	implements MultimapValidator<K, V, T>
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if any of the mandatory arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public MultimapValidatorImpl(ApplicationScope scope, Configuration configuration, String name, T value,
		Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public MultimapValidator<K, V, T> isEmpty()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.isEmpty(scope, this, name, null, null, 0).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else
		{
			// The size can change when invoked on a concurrent collection
			int size = value.size();
			if (size != 0)
			{
				addIllegalArgumentException(
					CollectionMessages.isEmpty(scope, this, getName(), value, name + ".size()", size).toString());
			}
		}
		return self();
	}

	@Override
	public MultimapValidator<K, V, T> isNotEmpty()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.isNotEmpty(scope, this, name).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else
		{
			// The size can change when invoked on a concurrent collection
			int size = value.size();
			if (size == 0)
			{
				addIllegalArgumentException(
					CollectionMessages.isNotEmpty(scope, this, name).toString());
			}
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator size()
	{
		if (hasFailed())
		{
			return new PrimitiveUnsignedIntegerValidatorImpl(scope, configuration, name + ".size()", 0,
				name, null, Pluralizer.ENTRY, context, failures);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
			return new PrimitiveUnsignedIntegerValidatorImpl(scope, configuration, name + ".size()", 0,
				name, null, Pluralizer.ENTRY, context, failures);
		}
		PrimitiveUnsignedIntegerValidatorImpl newValidator = new PrimitiveUnsignedIntegerValidatorImpl(scope,
			configuration, name + ".size()", value.size(), name, value, Pluralizer.ENTRY, context, failures);
		newValidator.putContext(value, name);
		return newValidator;
	}

	@Override
	public CollectionValidator<K, Set<K>> keySet()
	{
		if (hasFailed() || value == null)
		{
			return new CollectionValidatorImpl<>(scope, configuration,
				name + ".keySet()", null, Pluralizer.KEY, context, failures);
		}
		return new CollectionValidatorImpl<>(scope, configuration, name + ".keySet()", value.keySet(),
			Pluralizer.KEY, context, failures);
	}

	@Override
	public CollectionValidator<V, Collection<V>> values()
	{
		if (hasFailed() || value == null)
		{
			return new CollectionValidatorImpl<>(scope, configuration,
				name + ".values()", null, Pluralizer.VALUE, context, failures);
		}
		return new CollectionValidatorImpl<>(scope, configuration, name + ".values()", value.values(),
			Pluralizer.VALUE, context, failures);
	}

	@Override
	public CollectionValidator<Entry<K, V>, Collection<Entry<K, V>>> entries()
	{
		if (hasFailed() || value == null)
		{
			return new CollectionValidatorImpl<>(scope, configuration, name + ".entries()", null,
				Pluralizer.ENTRY, context, failures);
		}
		return new CollectionValidatorImpl<>(scope, configuration, name + ".entries()", value.entries(),
			Pluralizer.ENTRY, context, failures);
	}
}