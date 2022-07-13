/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ArrayValidator;
import com.github.cowwoc.requirements.java.ArrayVerifier;
import com.github.cowwoc.requirements.java.CollectionValidator;
import com.github.cowwoc.requirements.java.CollectionVerifier;
import com.github.cowwoc.requirements.java.ListValidator;
import com.github.cowwoc.requirements.java.ListVerifier;
import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.SizeVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Default implementation of {@code ArrayVerifier}.
 *
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public final class ArrayVerifierImpl<A, E>
	extends AbstractObjectVerifier<ArrayVerifier<A, E>, ArrayValidator<A, E>, A>
	implements ArrayVerifier<A, E>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public ArrayVerifierImpl(ArrayValidator<A, E> validator)
	{
		super(validator);
	}

	@Override
	protected ArrayVerifier<A, E> getThis()
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> isEmpty()
	{
		validator.isEmpty();
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> isNotEmpty()
	{
		validator.isNotEmpty();
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> contains(E expected)
	{
		validator.contains(expected);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> contains(E expected, String name)
	{
		validator.contains(expected, name);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> containsExactly(Collection<E> expected)
	{
		validator.containsExactly(expected);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> containsExactly(Collection<E> expected, String name)
	{
		validator.containsExactly(expected, name);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> containsAny(Collection<E> expected)
	{
		validator.containsAny(expected);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> containsAny(Collection<E> elements, String name)
	{
		validator.containsAny(elements, name);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> containsAll(Collection<E> expected)
	{
		validator.containsAll(expected);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> containsAll(Collection<E> expected, String name)
	{
		validator.containsAll(expected, name);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> doesNotContain(E element)
	{
		validator.doesNotContain(element);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> doesNotContain(E element, String name)
	{
		validator.doesNotContain(element, name);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainExactly(Collection<E> other)
	{
		validator.doesNotContainExactly(other);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainExactly(Collection<E> other, String name)
	{
		validator.doesNotContainExactly(other, name);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainAny(Collection<E> elements)
	{
		validator.doesNotContainAny(elements);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainAny(Collection<E> elements, String name)
	{
		validator.doesNotContainAny(elements, name);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainAll(Collection<E> elements)
	{
		validator.doesNotContainAll(elements);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainAll(Collection<E> elements, String name)
	{
		validator.doesNotContainAll(elements, name);
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainDuplicates()
	{
		validator.doesNotContainDuplicates();
		return validationResult();
	}

	@Override
	public ArrayVerifier<A, E> isSorted(Comparator<E> comparator)
	{
		validator.isSorted(comparator);
		return validationResult();
	}

	@Override
	public SizeVerifier length()
	{
		SizeValidator newValidator = validator.length();
		return validationResult(() -> new SizeVerifierImpl(newValidator));
	}

	@Override
	public ArrayVerifier<A, E> length(Consumer<SizeVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(length());
		return getThis();
	}

	@Override
	public CollectionVerifier<Collection<E>, E> asCollection()
	{
		CollectionValidator<Collection<E>, E> newValidator = validator.asCollection();
		return validationResult(() -> new CollectionVerifierImpl<>(newValidator));
	}

	@Override
	public ArrayVerifier<A, E> asCollection(Consumer<CollectionVerifier<Collection<E>, E>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asCollection());
		return getThis();
	}

	@Override
	public ListVerifier<List<E>, E> asList()
	{
		ListValidator<List<E>, E> newValidator = validator.asList();
		return validationResult(() -> new ListVerifierImpl<>(newValidator));
	}

	@Override
	public ArrayVerifier<A, E> asList(Consumer<ListVerifier<List<E>, E>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asList());
		return getThis();
	}
}
