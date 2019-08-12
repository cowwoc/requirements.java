/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ArrayValidator;
import org.bitbucket.cowwoc.requirements.java.CollectionValidator;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * An ArrayValidator that ignores subsequent validations due to an incompatible type conversion.
 *
 * @param <E> the type of elements in the array
 * @param <A> the type of the array
 */
public final class ArrayValidatorNoOp<E, A> extends AbstractObjectValidatorNoOp<ArrayValidator<E, A>, A>
	implements ArrayValidator<E, A>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	protected ArrayValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected ArrayValidator<E, A> getThis()
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> isEmpty()
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> isNotEmpty()
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> contains(E expected)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> contains(E expected, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> containsExactly(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> containsExactly(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> containsAny(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> containsAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> containsAll(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> containsAll(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> doesNotContainExactly(Collection<E> other)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> doesNotContainExactly(Collection<E> other, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> doesNotContainAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> doesNotContainAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E, A> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public SizeValidator length()
	{
		return new SizeValidatorNoOp(failures);
	}

	@Override
	public ArrayValidator<E, A> length(Consumer<SizeValidator> consumer)
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
	public ArrayValidator<E, A> asCollection(Consumer<CollectionValidator<Collection<E>, E>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
