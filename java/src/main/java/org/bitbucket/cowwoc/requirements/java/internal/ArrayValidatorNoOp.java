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
 * @param <E> the Object representation of the array elements
 */
public final class ArrayValidatorNoOp<E> extends AbstractObjectValidatorNoOp<ArrayValidator<E>, E[]>
	implements ArrayValidator<E>
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
	protected ArrayValidator<E> getThis()
	{
		return this;
	}

	@Override
	public ArrayValidator<E> isEmpty()
	{
		return this;
	}

	@Override
	public ArrayValidator<E> isNotEmpty()
	{
		return this;
	}

	@Override
	public ArrayValidator<E> contains(E expected)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> contains(E expected, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> containsExactly(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> containsExactly(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> containsAny(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> containsAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> containsAll(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> containsAll(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> doesNotContainExactly(Collection<E> other)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> doesNotContainExactly(Collection<E> other, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> doesNotContainAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> doesNotContainAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayValidator<E> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public SizeValidator length()
	{
		return new SizeValidatorNoOp(failures);
	}

	@Override
	public ArrayValidator<E> length(Consumer<SizeValidator> consumer)
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
	public ArrayValidator<E> asCollection(Consumer<CollectionValidator<Collection<E>, E>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
