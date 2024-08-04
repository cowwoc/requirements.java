/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator.noop;

import com.github.cowwoc.requirements10.java.validator.ListValidator;
import com.github.cowwoc.requirements10.java.validator.ObjectArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public final class ListValidatorNoOp
	extends AbstractObjectValidatorNoOp<ListValidator<List<Object>, Object>, List<Object>>
	implements ListValidator<List<Object>, Object>
{
	private static final ListValidatorNoOp INSTANCE = new ListValidatorNoOp();

	@SuppressWarnings("unchecked")
	public static <T extends List<E>, E> ListValidator<T, E> getInstance()
	{
		return (ListValidator<T, E>) INSTANCE;
	}

	private ListValidatorNoOp()
	{
	}

	@Override
	public List<Object> getValueOrDefault(List<Object> defaultValue)
	{
		return defaultValue;
	}

	@Override
	public ListValidator<List<Object>, Object> isSorted(Comparator<Object> comparator)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> isEmpty()
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> isNotEmpty()
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> contains(Object expected)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> contains(Object expected, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> containsExactly(Object[] expected)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> containsExactly(Collection<Object> expected)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> containsExactly(Object[] expected, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> containsExactly(Collection<Object> expected, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> containsAny(Object[] expected)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> containsAny(Collection<Object> expected)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> containsAny(Object[] expected, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> containsAny(Collection<Object> expected, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> containsAll(Object[] expected)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> containsAll(Collection<Object> expected)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> containsAll(Object[] expected, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> containsAll(Collection<Object> expected, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> containsSameNullity()
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContain(Object unwanted)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContain(Object unwanted, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContainExactly(Object[] unwanted)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContainExactly(Collection<Object> unwanted)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContainExactly(Object[] unwanted, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContainExactly(Collection<Object> unwanted, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContainAny(Object[] unwanted)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContainAny(Collection<Object> unwanted)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContainAny(Object[] unwanted, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContainAny(Collection<Object> unwanted, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContainAll(Object[] unwanted)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContainAll(Collection<Object> unwanted)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContainAll(Object[] unwanted, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContainAll(Collection<Object> unwanted, String name)
	{
		return this;
	}

	@Override
	public ListValidator<List<Object>, Object> doesNotContainDuplicates()
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
		return this;
	}
}