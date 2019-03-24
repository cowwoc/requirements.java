/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.StringVerifier;
import org.bitbucket.cowwoc.requirements.java.capabilities.ArrayCapabilities;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Extendable implementation of {@link ArrayCapabilities}.
 *
 * @param <S> the type of verifier that methods should return
 * @param <E> the Object representation of the array elements
 * @param <A> the type of the array
 */
public abstract class ArrayCapabilitiesImpl<S, E, A>
	extends ObjectCapabilitiesImpl<S, A>
	implements ArrayCapabilities<S, E, A>
{
	private final Collection<E> actualAsCollection;
	private final CollectionVerifier<Collection<E>, E> asCollection;

	/**
	 * @param scope              the application configuration
	 * @param name               the name of the value
	 * @param actual             the actual value
	 * @param actualAsCollection the {@code Collection} representation of the array
	 * @param config             the instance configuration
	 * @throws AssertionError if {@code name} or {@code config} are null. If {@code name} is empty.
	 */
	protected ArrayCapabilitiesImpl(ApplicationScope scope, String name, A actual,
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
	public PrimitiveNumberVerifier<Integer> length()
	{
		return new ContainerSizeVerifierImpl(scope, name, actual, name + ".length", actualAsCollection.size(),
			Pluralizer.ELEMENT, config);
	}

	@Override
	public S length(Consumer<PrimitiveNumberVerifier<Integer>> verifier)
	{
		verifier.accept(length());
		return getThis();
	}

	@Override
	public StringVerifier asString()
	{
		String value = config.toString(actual);
		return new StringVerifierImpl(scope, name, value, config);
	}

	@Override
	public S asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
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
		consumer.accept(asCollection());
		return getThis();
	}

	@Override
	public Optional<A> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	public A getActual()
	{
		return actual;
	}
}
