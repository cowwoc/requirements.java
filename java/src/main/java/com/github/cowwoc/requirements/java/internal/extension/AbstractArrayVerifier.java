/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.extension;

import com.github.cowwoc.requirements.java.CollectionValidator;
import com.github.cowwoc.requirements.java.CollectionVerifier;
import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.SizeVerifier;
import com.github.cowwoc.requirements.java.internal.CollectionVerifierImpl;
import com.github.cowwoc.requirements.java.internal.SizeVerifierImpl;
import com.github.cowwoc.requirements.java.extension.ExtensibleArrayValidator;
import com.github.cowwoc.requirements.java.extension.ExtensibleArrayVerifier;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * An implementation of {@code ExtensibleArrayVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <V> the type of validator used by the verifier
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public abstract class AbstractArrayVerifier<S, V extends ExtensibleArrayValidator<V, A, E>, A, E>
	extends AbstractObjectVerifier<S, V, A>
	implements ExtensibleArrayVerifier<S, A, E>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	protected AbstractArrayVerifier(V validator)
	{
		super(validator);
	}

	@Override
	public S isEmpty()
	{
		validator = validator.isEmpty();
		return validationResult();
	}

	@Override
	public S isNotEmpty()
	{
		validator = validator.isNotEmpty();
		return validationResult();
	}

	@Override
	public S contains(E expected)
	{
		validator = validator.contains(expected);
		return validationResult();
	}

	@Override
	public S contains(E expected, String name)
	{
		validator = validator.contains(expected, name);
		return validationResult();
	}

	@Override
	public S containsExactly(Collection<E> expected)
	{
		validator = validator.containsExactly(expected);
		return validationResult();
	}

	@Override
	public S containsExactly(Collection<E> expected, String name)
	{
		validator = validator.containsExactly(expected, name);
		return validationResult();
	}

	@Override
	public S containsAny(Collection<E> expected)
	{
		validator = validator.containsAny(expected);
		return validationResult();
	}

	@Override
	public S containsAny(Collection<E> elements, String name)
	{
		validator = validator.containsAny(elements, name);
		return validationResult();
	}

	@Override
	public S containsAll(Collection<E> expected)
	{
		validator = validator.containsAll(expected);
		return validationResult();
	}

	@Override
	public S containsAll(Collection<E> expected, String name)
	{
		validator = validator.containsAll(expected, name);
		return validationResult();
	}

	@Override
	public S doesNotContain(E element)
	{
		validator = validator.doesNotContain(element);
		return validationResult();
	}

	@Override
	public S doesNotContain(E element, String name)
	{
		validator = validator.doesNotContain(element, name);
		return validationResult();
	}

	@Override
	public S doesNotContainExactly(Collection<E> other)
	{
		validator = validator.doesNotContainExactly(other);
		return validationResult();
	}

	@Override
	public S doesNotContainExactly(Collection<E> other, String name)
	{
		validator = validator.doesNotContainExactly(other, name);
		return validationResult();
	}

	@Override
	public S doesNotContainAny(Collection<E> elements)
	{
		validator = validator.doesNotContainAny(elements);
		return validationResult();
	}

	@Override
	public S doesNotContainAny(Collection<E> elements, String name)
	{
		validator = validator.doesNotContainAny(elements, name);
		return validationResult();
	}

	@Override
	public S doesNotContainAll(Collection<E> elements)
	{
		validator = validator.doesNotContainAll(elements);
		return validationResult();
	}

	@Override
	public S doesNotContainAll(Collection<E> elements, String name)
	{
		validator = validator.doesNotContainAll(elements, name);
		return validationResult();
	}

	@Override
	public S doesNotContainDuplicates()
	{
		validator = validator.doesNotContainDuplicates();
		return validationResult();
	}

	@Override
	public SizeVerifier length()
	{
		SizeValidator newValidator = validator.length();
		return validationResult(() -> new SizeVerifierImpl(newValidator));
	}

	@Override
	public S length(Consumer<SizeVerifier> consumer)
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
	public S asCollection(Consumer<CollectionVerifier<Collection<E>, E>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asCollection());
		return getThis();
	}
}
