/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.type.CollectionValidator;
import com.github.cowwoc.requirements.java.type.MapValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveUnsignedIntegerValidator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @param <K> the type of keys in the map
 * @param <V> the type of values in the map
 * @param <T> the type of the map
 */
public final class MapValidatorImpl<K, V, T extends Map<K, V>>
	extends AbstractObjectValidator<MapValidator<K, V, T>, T>
	implements MapValidator<K, V, T>
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
	public MapValidatorImpl(ApplicationScope scope, Configuration configuration, String name, T value,
		Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public MapValidator<K, V, T> isEmpty()
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
					CollectionMessages.isEmpty(scope, this, name, value, name + ".size()", size).toString());
			}
		}
		return this;
	}

	@Override
	public MapValidator<K, V, T> isNotEmpty()
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
		return this;
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
		return new PrimitiveUnsignedIntegerValidatorImpl(scope, configuration, name + ".size()", value.size(),
			name, value, Pluralizer.ENTRY, context, failures).
			putContext(value, name);
	}

	@Override
	public CollectionValidator<K, Set<K>> keySet()
	{
		if (hasFailed())
		{
			return new CollectionValidatorImpl<>(scope, configuration, name + ".keySet()", null, Pluralizer.KEY,
				context, failures);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
			return new CollectionValidatorImpl<>(scope, configuration, name + ".keySet()", null, Pluralizer.KEY,
				context, failures);
		}
		return new CollectionValidatorImpl<>(scope, configuration, name + ".keySet()", value.keySet(),
			Pluralizer.KEY, context, failures);
	}

	@Override
	public CollectionValidator<V, Collection<V>> values()
	{
		if (hasFailed())
		{
			return new CollectionValidatorImpl<>(scope, configuration, name + ".values()", null, Pluralizer.VALUE,
				context, failures);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
			return new CollectionValidatorImpl<>(scope, configuration, name + ".values()", null, Pluralizer.VALUE,
				context, failures);
		}
		return new CollectionValidatorImpl<>(scope, configuration, name + ".values()", value.values(),
			Pluralizer.VALUE, context, failures);
	}

	@Override
	public CollectionValidator<Entry<K, V>, Set<Entry<K, V>>> entrySet()
	{
		if (hasFailed())
		{
			return new CollectionValidatorImpl<>(scope, configuration, name + ".entrySet()", null,
				Pluralizer.ENTRY, context, failures);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
			return new CollectionValidatorImpl<>(scope, configuration, name + ".entrySet()", null,
				Pluralizer.ENTRY, context, failures);
		}
		return new CollectionValidatorImpl<>(scope, configuration, name + ".entrySet()", value.entrySet(),
			Pluralizer.ENTRY, context, failures);
	}
}