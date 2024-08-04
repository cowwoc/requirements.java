/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator.noop;

import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.validator.CollectionValidator;
import com.github.cowwoc.requirements10.java.validator.MapValidator;
import com.github.cowwoc.requirements10.java.validator.ObjectValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator.VALUE_IS_UNDEFINED;

/**
 * @param <T> the type of the {@code Map}
 * @param <K> the type of keys in the {@code Map}
 * @param <V> the type of values in the {@code Map}
 */
public final class MapValidatorNoOp<T extends Map<K, V>, K, V>
	extends AbstractValidatorNoOp<MapValidator<T, K, V>, T>
	implements MapValidator<T, K, V>
{
	private static final MapValidatorNoOp<Map<Object, Object>, Object, Object> INSTANCE =
		new MapValidatorNoOp<>();

	@SuppressWarnings("unchecked")
	public static <T extends Map<K, V>, K, V> MapValidator<T, K, V> getInstance()
	{
		return (MapValidator<T, K, V>) INSTANCE;
	}

	private MapValidatorNoOp()
	{
	}

	@Override
	public CollectionValidator<Set<K>, K> keySet()
	{
		return CollectionValidatorNoOp.getInstance();
	}

	@Override
	public CollectionValidator<Collection<V>, V> values()
	{
		return CollectionValidatorNoOp.getInstance();
	}

	@Override
	public CollectionValidator<Set<Entry<K, V>>, Entry<K, V>> entrySet()
	{
		return CollectionValidatorNoOp.getInstance();
	}

	@Override
	public MapValidator<T, K, V> isEmpty()
	{
		return this;
	}

	@Override
	public MapValidator<T, K, V> isNotEmpty()
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator size()
	{
		return PrimitiveUnsignedIntegerValidatorNoOp.getInstance();
	}

	@Override
	public T getValue()
	{
		throw VALUE_IS_UNDEFINED.get();
	}

	@Override
	public MapValidator<T, K, V> isNull()
	{
		return this;
	}

	@Override
	public MapValidator<T, K, V> isNotNull()
	{
		return this;
	}

	@Override
	public MapValidator<T, K, V> isSameReferenceAs(Object expected, String name)
	{
		return this;
	}

	@Override
	public MapValidator<T, K, V> isNotSameReferenceAs(Object unwanted, String name)
	{
		return this;
	}

	@Override
	public <U> ObjectValidator<U> isInstanceOf(Class<U> expected)
	{
		return ObjectValidatorNoOp.getInstance();
	}

	@Override
	public <U> ObjectValidator<U> isInstanceOf(GenericType<U> expected)
	{
		return ObjectValidatorNoOp.getInstance();
	}

	@Override
	public MapValidator<T, K, V> isNotInstanceOf(Class<?> unwanted)
	{
		return this;
	}

	@Override
	public MapValidator<T, K, V> isNotInstanceOf(GenericType<?> unwanted)
	{
		return this;
	}

	@Override
	public MapValidator<T, K, V> isEqualTo(Object expected)
	{
		return this;
	}

	@Override
	public MapValidator<T, K, V> isEqualTo(Object expected, String name)
	{
		return this;
	}

	@Override
	public MapValidator<T, K, V> isNotEqualTo(Object unwanted)
	{
		return this;
	}

	@Override
	public MapValidator<T, K, V> isNotEqualTo(Object unwanted, String name)
	{
		return this;
	}
}