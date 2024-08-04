/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.message.ObjectMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.util.ObjectAndSize;
import com.github.cowwoc.requirements10.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements10.java.validator.CollectionValidator;
import com.github.cowwoc.requirements10.java.validator.MapValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @param <T> the type of the {@code Map}
 * @param <K> the type of keys in the {@code Map}
 * @param <V> the type of values in the {@code Map}
 */
public final class MapValidatorImpl<T extends Map<K, V>, K, V>
	extends AbstractObjectValidator<MapValidator<T, K, V>, T>
	implements MapValidator<T, K, V>
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
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public MapValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<T> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public MapValidator<T, K, V> isEmpty()
	{
		if (value.isNull())
			onNull();
		switch (value.test(Map::isEmpty))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isEmpty(this).toString());
		}
		return self();
	}

	@Override
	public MapValidator<T, K, V> isNotEmpty()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.isEmpty()))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isNotEmpty(this).toString());
		}
		return self();
	}

	@Override
	public CollectionValidator<Set<K>, K> keySet()
	{
		if (value.isNull())
			onNull();
		CollectionValidatorImpl<Set<K>, K> newValidator = new CollectionValidatorImpl<>(scope, configuration,
			name + ".keySet()", value.nullToUndefined().mapDefined(Map::keySet), Pluralizer.KEY, context,
			failures);
		value.ifDefined(value -> newValidator.withContext(value, name));
		return newValidator;
	}

	@Override
	public CollectionValidator<Collection<V>, V> values()
	{
		if (value.isNull())
			onNull();
		CollectionValidatorImpl<Collection<V>, V> newValidator = new CollectionValidatorImpl<>(scope,
			configuration,
			name + ".values()", value.nullToUndefined().mapDefined(Map::values), Pluralizer.VALUE, context,
			failures);
		value.ifDefined(value -> newValidator.withContext(value, name));
		return newValidator;
	}

	@Override
	public CollectionValidator<Set<Entry<K, V>>, Entry<K, V>> entrySet()
	{
		if (value.isNull())
			onNull();
		CollectionValidatorImpl<Set<Entry<K, V>>, Entry<K, V>> newValidator = new CollectionValidatorImpl<>(
			scope, configuration, name + ".entrySet()", value.nullToUndefined().mapDefined(Map::entrySet),
			Pluralizer.ENTRY, context, failures);
		value.ifDefined(value -> newValidator.withContext(value, name));
		return newValidator;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator size()
	{
		if (value.isNull())
			onNull();
		return new ObjectSizeValidatorImpl(scope, configuration, name,
			value.nullToUndefined().mapDefined(value -> new ObjectAndSize(value, value.size())),
			name + ".size()", Pluralizer.ENTRY, context, failures);
	}
}