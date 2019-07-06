/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ArrayValidator;
import org.bitbucket.cowwoc.requirements.java.CollectionValidator;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * A CollectionValidator that ignores subsequent validations due to an incompatible type conversion.
 *
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 */
public final class CollectionValidatorNoOp<C extends Collection<E>, E>
	extends AbstractObjectValidatorNoOp<CollectionValidator<C, E>, C>
	implements CollectionValidator<C, E>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null. If {@code name}
	 *                        is empty.
	 */
	public CollectionValidatorNoOp(ApplicationScope scope, Configuration config,
	                               List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	protected CollectionValidator<C, E> getThis()
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> isEmpty()
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> isNotEmpty()
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> contains(E element)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> contains(E element, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> containsExactly(Collection<E> expected)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> containsExactly(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> containsAny(Collection<E> expected)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> containsAny(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> containsAll(Collection<E> expected)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> containsAll(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> doesNotContainExactly(Collection<E> other)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> doesNotContainExactly(Collection<E> other, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> doesNotContainAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> doesNotContainAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionValidator<C, E> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public SizeValidator size()
	{
		return new SizeValidatorNoOp(scope, config, failures);
	}

	@Override
	public CollectionValidator<C, E> size(Consumer<SizeValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public ArrayValidator<E> asArray(Class<E> type)
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		return new ArrayValidatorNoOp<>(scope, config, failures);
	}

	@Override
	public CollectionValidator<C, E> asArray(Class<E> type, Consumer<ArrayValidator<E>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}