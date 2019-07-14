/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.CollectionValidator;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleArrayValidator;
import org.bitbucket.cowwoc.requirements.java.internal.CollectionValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.SizeValidatorNoOp;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * An {@code ExtensibleArrayValidator} that does nothing. A validator that ignores all subsequent
 * failures because they are guaranteed to fail and would add any value to the end-user. For example, an
 * attempt was made to dereference null or cast the value to an incompatible type.
 *
 * @param <S> the type of validator returned by the methods
 * @param <E> the type of elements in the array
 * @param <A> the type of the array
 */
public abstract class AbstractArrayValidatorNoOp<S, E, A>
	extends AbstractObjectValidatorNoOp<S, A>
	implements ExtensibleArrayValidator<S, E, A>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public AbstractArrayValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	public S isEmpty()
	{
		return getThis();
	}

	@Override
	public S isNotEmpty()
	{
		return getThis();
	}

	@Override
	public S contains(E expected)
	{
		return getThis();
	}

	@Override
	public S contains(E expected, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return getThis();
	}

	@Override
	public S containsExactly(Collection<E> expected)
	{
		return getThis();
	}

	@Override
	public S containsExactly(Collection<E> expected, String name)
	{
		return getThis();
	}

	@Override
	public S containsAny(Collection<E> expected)
	{
		return getThis();
	}

	@Override
	public S containsAny(Collection<E> expected, String name)
	{
		return getThis();
	}

	@Override
	public S containsAll(Collection<E> expected)
	{
		return getThis();
	}

	@Override
	public S containsAll(Collection<E> expected, String name)
	{
		return getThis();
	}

	@Override
	public S doesNotContain(E element)
	{
		return getThis();
	}

	@Override
	public S doesNotContain(E element, String name)
	{
		return getThis();
	}

	@Override
	public S doesNotContainExactly(Collection<E> element)
	{
		return getThis();
	}

	@Override
	public S doesNotContainExactly(Collection<E> element, String name)
	{
		return getThis();
	}

	@Override
	public S doesNotContainAny(Collection<E> elements)
	{
		return getThis();
	}

	@Override
	public S doesNotContainAny(Collection<E> element, String names)
	{
		return getThis();
	}

	@Override
	public S doesNotContainAll(Collection<E> elements)
	{
		return getThis();
	}

	@Override
	public S doesNotContainAll(Collection<E> element, String names)
	{
		return getThis();
	}

	@Override
	public S doesNotContainDuplicates()
	{
		return getThis();
	}

	@Override
	public SizeValidator length()
	{
		return new SizeValidatorNoOp(getFailures());
	}

	@Override
	public S length(Consumer<SizeValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return getThis();
	}

	@Override
	public CollectionValidator<Collection<E>, E> asCollection()
	{
		return new CollectionValidatorNoOp<>(getFailures());
	}

	@Override
	public S asCollection(Consumer<CollectionValidator<Collection<E>, E>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return getThis();
	}
}
