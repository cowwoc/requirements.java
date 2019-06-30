/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.SizeVerifier;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.AbstractObjectVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.CollectionVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.SizeVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * An implementation of {@code ExtensibleArrayVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <E> the Object representation of the array elements
 * @param <A> the type of the array
 */
public abstract class AbstractArrayVerifier<S, E, A>
	extends AbstractObjectVerifier<S, A>
	implements ExtensibleArrayVerifier<S, E, A>
{
	private final Collection<E> actualAsCollection;
	private final CollectionVerifier<Collection<E>, E> asCollection;

	/**
	 * @param scope              the application configuration
	 * @param name               the name of the value
	 * @param actual             the actual value
	 * @param actualAsCollection the {@code Collection} representation of the array
	 * @param config             the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	protected AbstractArrayVerifier(ApplicationScope scope, String name, A actual,
	                                Collection<E> actualAsCollection, Configuration config)
	{
		super(scope, name, actual, config);
		this.actualAsCollection = actualAsCollection;
		this.asCollection = new CollectionVerifierImpl<>(scope, name, actualAsCollection, Pluralizer.ELEMENT,
			config);
	}

	@Override
	public S isEqualTo(Object expected)
	{
		asCollection.isEqualTo(expected);
		return getThis();
	}

	@Override
	public S isEqualTo(Object expected, String name)
	{
		asCollection.isEqualTo(expected, name);
		return getThis();
	}

	@Override
	public S isNotEqualTo(Object value)
	{
		asCollection.isNotEqualTo(value);
		return getThis();
	}

	@Override
	public S isNotEqualTo(Object value, String name)
	{
		asCollection.isNotEqualTo(value, name);
		return getThis();
	}

	@Override
	public S isInstanceOf(Class<?> type)
	{
		asCollection.isInstanceOf(type);
		return getThis();
	}

	@Override
	public S isNotInstanceOf(Class<?> type)
	{
		asCollection.isNotInstanceOf(type);
		return getThis();
	}

	@Override
	public S isNull()
	{
		asCollection.isNull();
		return getThis();
	}

	@Override
	public S isNotNull()
	{
		asCollection.isNotNull();
		return getThis();
	}

	@Override
	public S isEmpty()
	{
		asCollection.isEmpty();
		return getThis();
	}

	@Override
	public S isNotEmpty()
	{
		asCollection.isNotEmpty();
		return getThis();
	}

	@Override
	public S contains(E expected)
	{
		asCollection.contains(expected);
		return getThis();
	}

	@Override
	public S contains(E expected, String name)
	{
		asCollection.contains(expected, name);
		return getThis();
	}

	@Override
	public S containsExactly(Collection<E> expected)
	{
		asCollection.containsExactly(expected);
		return getThis();
	}

	@Override
	public S containsExactly(Collection<E> expected, String name)
	{
		asCollection.containsExactly(expected, name);
		return getThis();
	}

	@Override
	public S containsAny(Collection<E> expected)
	{
		asCollection.containsAny(expected);
		return getThis();
	}

	@Override
	public S containsAny(Collection<E> elements, String name)
	{
		asCollection.containsAny(elements, name);
		return getThis();
	}

	@Override
	public S containsAll(Collection<E> expected)
	{
		asCollection.containsAll(expected);
		return getThis();
	}

	@Override
	public S containsAll(Collection<E> expected, String name)
	{
		asCollection.containsAll(expected, name);
		return getThis();
	}

	@Override
	public S doesNotContain(E element)
	{
		asCollection.doesNotContain(element);
		return getThis();
	}

	@Override
	public S doesNotContain(E element, String name)
	{
		asCollection.doesNotContain(element, name);
		return getThis();
	}

	@Override
	public S doesNotContainExactly(Collection<E> other)
	{
		asCollection.doesNotContainExactly(other);
		return getThis();
	}

	@Override
	public S doesNotContainExactly(Collection<E> other, String name)
	{
		asCollection.doesNotContainExactly(other, name);
		return getThis();
	}

	@Override
	public S doesNotContainAny(Collection<E> elements)
	{
		asCollection.doesNotContainAny(elements);
		return getThis();
	}

	@Override
	public S doesNotContainAny(Collection<E> elements, String name)
	{
		asCollection.doesNotContainAny(elements, name);
		return getThis();
	}

	@Override
	public S doesNotContainAll(Collection<E> elements)
	{
		asCollection.doesNotContainAll(elements);
		return getThis();
	}

	@Override
	public S doesNotContainAll(Collection<E> elements, String name)
	{
		asCollection.doesNotContainAll(elements, name);
		return getThis();
	}

	@Override
	public S doesNotContainDuplicates()
	{
		asCollection.doesNotContainDuplicates();
		return getThis();
	}

	@Override
	public SizeVerifier length()
	{
		return new SizeVerifierImpl(scope, name, actual, name + ".length", actualAsCollection.size(),
			Pluralizer.ELEMENT, config);
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
		return asCollection;
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
