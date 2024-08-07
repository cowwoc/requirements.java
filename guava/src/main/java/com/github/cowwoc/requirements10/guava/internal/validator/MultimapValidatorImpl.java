/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.guava.internal.validator;

import com.github.cowwoc.requirements10.guava.validator.MultimapValidator;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.message.ObjectMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.util.ObjectAndSize;
import com.github.cowwoc.requirements10.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractObjectValidator;
import com.github.cowwoc.requirements10.java.internal.validator.CollectionValidatorImpl;
import com.github.cowwoc.requirements10.java.internal.validator.ObjectSizeValidatorImpl;
import com.github.cowwoc.requirements10.java.validator.CollectionValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	 * @param value         the value
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public MultimapValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<T> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public MultimapValidator<T, K, V> isEmpty()
	{
		if (value.isNull())
			onNull();
		switch (value.test(Multimap::isEmpty))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isEmpty(this).toString());
		}
		return self();
	}

	@Override
	public MultimapValidator<T, K, V> isNotEmpty()
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
			name + ".keySet()", value.nullToUndefined().mapDefined(Multimap::keySet), Pluralizer.KEY, context,
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
			configuration, name + ".values()", value.nullToUndefined().mapDefined(Multimap::values),
			Pluralizer.VALUE, context, failures);
		value.ifDefined(value -> newValidator.withContext(value, name));
		return newValidator;
	}

	@Override
	public CollectionValidator<Collection<Entry<K, V>>, Entry<K, V>> entries()
	{
		if (value.isNull())
			onNull();
		CollectionValidatorImpl<Collection<Entry<K, V>>, Entry<K, V>> newValidator =
			new CollectionValidatorImpl<>(scope, configuration, name + ".entrySet()",
				value.nullToUndefined().mapDefined(Multimap::entries), Pluralizer.ENTRY, context, failures);
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
			name + ".size()",
			Pluralizer.ELEMENT, context, failures);
	}
}