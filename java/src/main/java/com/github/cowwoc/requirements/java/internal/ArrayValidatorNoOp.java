/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ArrayValidator;
import com.github.cowwoc.requirements.java.CollectionValidator;
import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * An ArrayValidator that ignores subsequent validations due to an incompatible type conversion.
 *
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public final class ArrayValidatorNoOp<A, E> extends AbstractObjectValidatorNoOp<ArrayValidator<A, E>, A>
	implements ArrayValidator<A, E>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public ArrayValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected ArrayValidator<A, E> getThis()
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> isEmpty()
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> isNotEmpty()
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> contains(E expected)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> contains(E expected, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> containsExactly(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> containsExactly(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> containsAny(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> containsAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> containsAll(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> containsAll(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> doesNotContainExactly(Collection<E> other)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> doesNotContainExactly(Collection<E> other, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> doesNotContainAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> doesNotContainAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public SizeValidator length()
	{
		return new SizeValidatorNoOp(failures);
	}

	@Override
	public ArrayValidator<A, E> length(Consumer<SizeValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public CollectionValidator<Collection<E>, E> asCollection()
	{
		return new CollectionValidatorNoOp<>(failures);
	}

	@Override
	public ArrayValidator<A, E> asCollection(Consumer<CollectionValidator<Collection<E>, E>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
