/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ArrayValidator;
import com.github.cowwoc.requirements.java.ArrayVerifier;
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
 * An implementation of {@code ListVerifier}.
 *
 * @param <L> the type of the list
 * @param <E> the type of elements in the list
 */
public final class ListVerifierImpl<L extends List<E>, E>
	extends AbstractObjectVerifier<ListVerifier<L, E>, ListValidator<L, E>, L>
	implements ListVerifier<L, E>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public ListVerifierImpl(ListValidator<L, E> validator)
	{
		super(validator);
	}

	@Override
	protected ListVerifier<L, E> getThis()
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> isEmpty()
	{
		validator.isEmpty();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> isNotEmpty()
	{
		validator.isNotEmpty();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> contains(E element)
	{
		validator.contains(element);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> contains(E element, String name)
	{
		validator.contains(element, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> containsExactly(Collection<E> expected)
	{
		validator.containsExactly(expected);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> containsExactly(Collection<E> expected, String name)
	{
		validator.containsExactly(expected, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> containsAny(Collection<E> expected)
	{
		validator.containsAny(expected);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> containsAny(Collection<E> expected, String name)
	{
		validator.containsAny(expected, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> containsAll(Collection<E> expected)
	{
		validator.containsAll(expected);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> containsAll(Collection<E> expected, String name)
	{
		validator.containsAll(expected, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> doesNotContain(E element)
	{
		validator.doesNotContain(element);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> doesNotContain(E element, String name)
	{
		validator.doesNotContain(element, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> doesNotContainExactly(Collection<E> other)
	{
		validator.doesNotContainExactly(other);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> doesNotContainExactly(Collection<E> other, String name)
	{
		validator.doesNotContainExactly(other, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> doesNotContainAny(Collection<E> elements)
	{
		validator.doesNotContainAny(elements);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> doesNotContainAny(Collection<E> elements, String name)
	{
		validator.doesNotContainAny(elements, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> doesNotContainAll(Collection<E> elements)
	{
		validator.doesNotContainAll(elements);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> doesNotContainAll(Collection<E> elements, String name)
	{
		validator.doesNotContainAll(elements, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> doesNotContainDuplicates()
	{
		validator.doesNotContainDuplicates();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public ListVerifier<L, E> isSorted(Comparator<E> comparator)
	{
		validator.isSorted(comparator);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier size()
	{
		SizeValidator newValidator = validator.size();
		return validationResult(() -> new SizeVerifierImpl(newValidator));
	}

	@Override
	public ListVerifier<L, E> size(Consumer<SizeVerifier> consumer)
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
	public ListVerifier<L, E> asArray(Class<E> type, Consumer<ArrayVerifier<E[], E>> consumer)
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asArray(type));
		return this;
	}
}
