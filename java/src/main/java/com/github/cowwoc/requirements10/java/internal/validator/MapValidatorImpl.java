/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.message.CollectionMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.ValidationTarget;
import com.github.cowwoc.requirements10.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements10.java.validator.CollectionValidator;
import com.github.cowwoc.requirements10.java.validator.MapValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
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
	 * @param value         the value being validated
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public MapValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<T> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public MapValidator<T, K, V> isEmpty()
	{
		if (value.validationFailed(Map::isEmpty))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.isEmptyFailed(this).toString());
		}
		return self();
	}

	@Override
	public MapValidator<T, K, V> isNotEmpty()
	{
		if (value.validationFailed(v -> !v.isEmpty()))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.isNotEmptyFailed(this).toString());
		}
		return self();
	}

	@Override
	public CollectionValidator<Set<K>, K> keySet()
	{
		failOnNull();
		ValidationTarget<T> nullToInvalid = value.nullToInvalid();
		CollectionValidatorImpl<Set<K>, K> newValidator = new CollectionValidatorImpl<>(scope, configuration,
			name + ".keySet()", nullToInvalid.map(Map::keySet), Pluralizer.KEY, context, failures);
		nullToInvalid.ifValid(v -> newValidator.withContext(v, name));
		return newValidator;
	}

	@Override
	public CollectionValidator<Collection<V>, V> values()
	{
		failOnNull();
		ValidationTarget<T> nullToInvalid = value.nullToInvalid();
		CollectionValidatorImpl<Collection<V>, V> newValidator = new CollectionValidatorImpl<>(scope,
			configuration, name + ".values()", nullToInvalid.map(Map::values), Pluralizer.VALUE, context,
			failures);
		nullToInvalid.ifValid(v -> newValidator.withContext(v, name));
		return newValidator;
	}

	@Override
	public CollectionValidator<Set<Entry<K, V>>, Entry<K, V>> entrySet()
	{
		failOnNull();
		ValidationTarget<T> nullToInvalid = value.nullToInvalid();
		CollectionValidatorImpl<Set<Entry<K, V>>, Entry<K, V>> newValidator = new CollectionValidatorImpl<>(
			scope, configuration, name + ".entrySet()", nullToInvalid.map(Map::entrySet),
			Pluralizer.ENTRY, context, failures);
		nullToInvalid.ifValid(v -> newValidator.withContext(v, name));
		return newValidator;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator size()
	{
		failOnNull();
		return new ObjectSizeValidatorImpl(scope, configuration, this, name + ".size()",
			value.nullToInvalid().map(Map::size), Pluralizer.ENTRY, context, failures);
	}
}