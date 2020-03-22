/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ArrayValidator;
import org.bitbucket.cowwoc.requirements.java.ListValidator;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * An ArrayValidator that ignores subsequent validations due to an incompatible type conversion.
 *
 * @param <E> the type of elements in the list
 * @param <L> the type of the list
 */
public final class ListValidatorNoOp<L extends List<E>, E>
	extends AbstractObjectValidatorNoOp<ListValidator<L, E>, L>
	implements ListValidator<L, E>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public ListValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected ListValidator<L, E> getThis()
	{
		return this;
	}

	@Override
	public ListValidator<L, E> isEmpty()
	{
		return this;
	}

	@Override
	public ListValidator<L, E> isNotEmpty()
	{
		return this;
	}

	@Override
	public ListValidator<L, E> contains(E expected)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> contains(E expected, String name)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> containsExactly(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> containsExactly(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> containsAny(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> containsAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> containsAll(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> containsAll(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> doesNotContainExactly(Collection<E> other)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> doesNotContainExactly(Collection<E> other, String name)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> doesNotContainAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> doesNotContainAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ListValidator<L, E> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public SizeValidator size()
	{
		return new SizeValidatorNoOp(failures);
	}

	@Override
	public ListValidator<L, E> size(Consumer<SizeValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public ArrayValidator<E[], E> asArray(Class<E> type)
	{
		return new ArrayValidatorNoOp<>(failures);
	}

	@Override
	public ListValidator<L, E> asArray(Class<E> type, Consumer<ArrayValidator<E[], E>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}