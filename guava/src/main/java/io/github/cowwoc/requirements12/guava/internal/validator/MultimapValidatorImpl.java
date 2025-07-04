/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.guava.internal.validator;

import com.google.common.collect.Multimap;
import io.github.cowwoc.requirements12.java.ValidationFailure;
import io.github.cowwoc.requirements12.java.internal.Configuration;
import io.github.cowwoc.requirements12.java.internal.message.ObjectMessages;
import io.github.cowwoc.requirements12.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements12.java.internal.util.Pluralizer;
import io.github.cowwoc.requirements12.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements12.java.internal.validator.AbstractObjectValidator;
import io.github.cowwoc.requirements12.java.internal.validator.CollectionValidatorImpl;
import io.github.cowwoc.requirements12.java.internal.validator.ObjectSizeValidatorImpl;
import io.github.cowwoc.requirements12.java.validator.CollectionValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveUnsignedIntegerValidator;
import io.github.cowwoc.requirements12.guava.validator.MultimapValidator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

/**
 * @param <T> the type of the {@code Multimap}
 * @param <K> the type of keys in the {@code Multimap}
 * @param <V> the type of values in the {@code Multimap}
 */
public final class MultimapValidatorImpl<K, V, T extends Multimap<K, V>>
	extends AbstractObjectValidator<MultimapValidator<T, K, V>, T>
	implements MultimapValidator<T, K, V>
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
	public MultimapValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<T> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public MultimapValidator<T, K, V> isEmpty()
	{
		if (value.validationFailed(Multimap::isEmpty))
		{
			failOnNull();
			addIllegalArgumentException(
				ObjectMessages.isEmptyFailed(this).toString());
		}
		return self();
	}

	@Override
	public MultimapValidator<T, K, V> isNotEmpty()
	{
		if (value.validationFailed(v -> !v.isEmpty()))
		{
			failOnNull();
			addIllegalArgumentException(
				ObjectMessages.isNotEmptyFailed(this).toString());
		}
		return self();
	}

	@Override
	public CollectionValidator<Set<K>, K> keySet()
	{
		failOnNull();
		ValidationTarget<T> nullToInvalid = value.nullToInvalid();
		CollectionValidatorImpl<Set<K>, K> newValidator = new CollectionValidatorImpl<>(scope, configuration,
			name + ".keySet()", nullToInvalid.map(Multimap::keySet), Pluralizer.KEY, context, failures);
		nullToInvalid.ifValid(v -> newValidator.withContext(v, name));
		return newValidator;
	}

	@Override
	public CollectionValidator<Collection<V>, V> values()
	{
		failOnNull();
		ValidationTarget<T> nullToInvalid = value.nullToInvalid();
		CollectionValidatorImpl<Collection<V>, V> newValidator = new CollectionValidatorImpl<>(scope,
			configuration, name + ".values()", nullToInvalid.map(Multimap::values), Pluralizer.VALUE,
			context, failures);
		nullToInvalid.ifValid(v -> newValidator.withContext(v, name));
		return newValidator;
	}

	@Override
	public CollectionValidator<Collection<Entry<K, V>>, Entry<K, V>> entries()
	{
		failOnNull();
		ValidationTarget<T> nullToInvalid = value.nullToInvalid();
		CollectionValidatorImpl<Collection<Entry<K, V>>, Entry<K, V>> newValidator =
			new CollectionValidatorImpl<>(scope, configuration, name + ".entrySet()",
				nullToInvalid.map(Multimap::entries), Pluralizer.ENTRY, context, failures);
		nullToInvalid.ifValid(v -> newValidator.withContext(v, name));
		return newValidator;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator size()
	{
		failOnNull();
		return new ObjectSizeValidatorImpl(scope, configuration, this, name + ".size()",
			value.nullToInvalid().map(Multimap::size), Pluralizer.ELEMENT, context, failures);
	}
}