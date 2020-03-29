/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ArrayValidator;
import com.github.cowwoc.requirements.java.ArrayVerifier;
import com.github.cowwoc.requirements.java.CollectionValidator;
import com.github.cowwoc.requirements.java.CollectionVerifier;
import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.SizeVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * An implementation of {@code CollectionVerifier}.
 *
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 */
public final class CollectionVerifierImpl<C extends Collection<E>, E>
	extends AbstractObjectVerifier<CollectionVerifier<C, E>, CollectionValidator<C, E>, C>
	implements CollectionVerifier<C, E>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public CollectionVerifierImpl(CollectionValidator<C, E> validator)
	{
		super(validator);
	}

	@Override
	protected CollectionVerifier<C, E> getThis()
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> isEmpty()
	{
		validator.isEmpty();
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> isNotEmpty()
	{
		validator.isNotEmpty();
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> contains(E element)
	{
		validator.contains(element);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> contains(E element, String name)
	{
		validator.contains(element, name);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> containsExactly(Collection<E> expected)
	{
		validator.containsExactly(expected);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> containsExactly(Collection<E> expected, String name)
	{
		validator.containsExactly(expected, name);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> containsAny(Collection<E> expected)
	{
		validator.containsAny(expected);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> containsAny(Collection<E> expected, String name)
	{
		validator.containsAny(expected, name);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> containsAll(Collection<E> expected)
	{
		validator.containsAll(expected);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> containsAll(Collection<E> expected, String name)
	{
		validator.containsAll(expected, name);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContain(E element)
	{
		validator.doesNotContain(element);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContain(E element, String name)
	{
		validator.doesNotContain(element, name);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainExactly(Collection<E> other)
	{
		validator.doesNotContainExactly(other);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainExactly(Collection<E> other, String name)
	{
		validator.doesNotContainExactly(other, name);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAny(Collection<E> elements)
	{
		validator.doesNotContainAny(elements);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAny(Collection<E> elements, String name)
	{
		validator.doesNotContainAny(elements, name);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAll(Collection<E> elements)
	{
		validator.doesNotContainAll(elements);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAll(Collection<E> elements, String name)
	{
		validator.doesNotContainAll(elements, name);
		return validationResult();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainDuplicates()
	{
		validator.doesNotContainDuplicates();
		return validationResult();
	}

	@Override
	public SizeVerifier size()
	{
		SizeValidator newValidator = validator.size();
		return validationResult(() -> new SizeVerifierImpl(newValidator));
	}

	@Override
	public CollectionVerifier<C, E> size(Consumer<SizeVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(size());
		return this;
	}

	@Override
	public ArrayVerifier<E[], E> asArray(Class<E> type)
	{
		ArrayValidator<E[], E> newValidator = validator.asArray(type);
		return validationResult(() -> new ArrayVerifierImpl<>(newValidator));
	}

	@Override
	public CollectionVerifier<C, E> asArray(Class<E> type, Consumer<ArrayVerifier<E[], E>> consumer)
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asArray(type));
		return this;
	}
}
