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
import java.util.Comparator;
import java.util.List;

public final class ObjectArrayValidatorNoOp
	extends AbstractObjectValidatorNoOp<ObjectArrayValidator<Object[], Object>, Object[]>
	implements ObjectArrayValidator<Object[], Object>
{
	private static final ObjectArrayValidatorNoOp INSTANCE = new ObjectArrayValidatorNoOp();

	@SuppressWarnings("unchecked")
	public static <T, E> ObjectArrayValidator<T, E> getInstance()
	{
		return (ObjectArrayValidator<T, E>) INSTANCE;
	}

	private ObjectArrayValidatorNoOp()
	{
	}

	@Override
	public ObjectArrayValidator<Object[], Object> containsSameNullity()
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> isEmpty()
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> isNotEmpty()
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> contains(Object expected)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> contains(Object expected, String name)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> doesNotContain(Object unwanted)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> doesNotContain(Object unwanted, String name)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> containsExactly(Object[] expected)
	{
		return this;
	}

	@Override
	public <C extends Collection<Object>> ObjectArrayValidator<Object[], Object> containsExactly(C expected)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> containsExactly(Object[] expected, String name)
	{
		return this;
	}

	@Override
	public <C extends Collection<Object>> ObjectArrayValidator<Object[], Object> containsExactly(C expected,
		String name)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> doesNotContainExactly(Object[] unwanted)
	{
		return this;
	}

	@Override
	public <C extends Collection<Object>> ObjectArrayValidator<Object[], Object> doesNotContainExactly(
		C unwanted)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> doesNotContainExactly(Object[] unwanted, String name)
	{
		return this;
	}

	@Override
	public <C extends Collection<Object>> ObjectArrayValidator<Object[], Object> doesNotContainExactly(
		C unwanted, String name)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> containsAny(Object[] expected)
	{
		return this;
	}

	@Override
	public <C extends Collection<Object>> ObjectArrayValidator<Object[], Object> containsAny(C expected)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> containsAny(Object[] expected, String name)
	{
		return this;
	}

	@Override
	public <C extends Collection<Object>> ObjectArrayValidator<Object[], Object> containsAny(C expected,
		String name)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> doesNotContainAny(Object[] unwanted)
	{
		return this;
	}

	@Override
	public <C extends Collection<Object>> ObjectArrayValidator<Object[], Object> doesNotContainAny(C unwanted)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> doesNotContainAny(Object[] unwanted, String name)
	{
		return this;
	}

	@Override
	public <C extends Collection<Object>> ObjectArrayValidator<Object[], Object> doesNotContainAny(C unwanted,
		String name)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> containsAll(Object[] expected)
	{
		return this;
	}

	@Override
	public <C extends Collection<Object>> ObjectArrayValidator<Object[], Object> containsAll(C expected)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> containsAll(Object[] expected, String name)
	{
		return this;
	}

	@Override
	public <C extends Collection<Object>> ObjectArrayValidator<Object[], Object> containsAll(C expected,
		String name)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> doesNotContainAll(Object[] unwanted)
	{
		return this;
	}

	@Override
	public <C extends Collection<Object>> ObjectArrayValidator<Object[], Object> doesNotContainAll(C unwanted)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> doesNotContainAll(Object[] unwanted, String name)
	{
		return this;
	}

	@Override
	public <C extends Collection<Object>> ObjectArrayValidator<Object[], Object> doesNotContainAll(C unwanted,
		String name)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> isSorted(Comparator<Object> comparator)
	{
		return this;
	}

	@Override
	public ObjectArrayValidator<Object[], Object> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator length()
	{
		return PrimitiveUnsignedIntegerValidatorNoOp.getInstance();
	}

	@Override
	public CollectionValidator<Collection<Object>, Object> asCollection()
	{
		return CollectionValidatorNoOp.getInstance();
	}

	@Override
	public ListValidator<List<Object>, Object> asList()
	{
		return ListValidatorNoOp.getInstance();
	}
}