/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator.noop;

import com.github.cowwoc.requirements10.java.validator.CollectionValidator;
import com.github.cowwoc.requirements10.java.validator.ListValidator;
import com.github.cowwoc.requirements10.java.validator.ObjectArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;

import java.util.Collection;
import java.util.List;

public final class CollectionValidatorNoOp
	extends AbstractObjectValidatorNoOp<CollectionValidator<Collection<Object>, Object>, Collection<Object>>
	implements CollectionValidator<Collection<Object>, Object>
{
	private static final CollectionValidatorNoOp INSTANCE = new CollectionValidatorNoOp();

	@SuppressWarnings("unchecked")
	public static <T extends Collection<E>, E> CollectionValidator<T, E> getInstance()
	{
		return (CollectionValidator<T, E>) INSTANCE;
	}

	private CollectionValidatorNoOp()
	{
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> isEmpty()
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> isNotEmpty()
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> contains(Object expected)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> contains(Object expected, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> containsExactly(Object[] expected)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> containsExactly(Collection<Object> expected)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> containsExactly(Object[] expected, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> containsExactly(Collection<Object> expected,
		String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> containsAny(Object[] expected)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> containsAny(Collection<Object> expected)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> containsAny(Object[] expected, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> containsAny(Collection<Object> expected, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> containsAll(Object[] expected)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> containsAll(Collection<Object> expected)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> containsAll(Object[] expected, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> containsAll(Collection<Object> expected, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> containsSameNullity()
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContain(Object unwanted)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContain(Object unwanted, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContainExactly(Object[] unwanted)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContainExactly(Collection<Object> unwanted)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContainExactly(Object[] unwanted, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContainExactly(Collection<Object> unwanted,
		String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContainAny(Object[] unwanted)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContainAny(Collection<Object> unwanted)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContainAny(Object[] unwanted, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContainAny(Collection<Object> unwanted,
		String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContainAll(Object[] unwanted)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContainAll(Collection<Object> unwanted)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContainAll(Object[] unwanted, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContainAll(Collection<Object> unwanted,
		String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator size()
	{
		return PrimitiveUnsignedIntegerValidatorNoOp.getInstance();
	}

	@Override
	public ObjectArrayValidator<Object[], Object> asArray(Class<Object> type)
	{
		return ObjectArrayValidatorNoOp.getInstance();
	}

	@Override
	public ListValidator<List<Object>, Object> asList()
	{
		return ListValidatorNoOp.getInstance();
	}
}