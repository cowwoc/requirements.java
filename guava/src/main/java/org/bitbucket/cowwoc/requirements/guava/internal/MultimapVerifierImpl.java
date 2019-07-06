/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava.internal;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.guava.MultimapValidator;
import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;
import org.bitbucket.cowwoc.requirements.java.CollectionValidator;
import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.SizeVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.CollectionVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.SizeVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Default implementation of MultimapVerifier.
 *
 * @param <K> the type of key in the multimap
 * @param <V> the type of value in the multimap
 */
public final class MultimapVerifierImpl<K, V>
	extends AbstractObjectVerifier<MultimapVerifier<K, V>, MultimapValidator<K, V>, Multimap<K, V>>
	implements MultimapVerifier<K, V>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public MultimapVerifierImpl(MultimapValidator<K, V> validator)
	{
		super(validator);
	}

	@Override
	protected MultimapVerifier<K, V> getThis()
	{
		return this;
	}

	@Override
	public CollectionVerifier<Set<K>, K> keySet()
	{
		CollectionValidator<Set<K>, K> newValidator = validator.keySet();
		return validationResult(() -> new CollectionVerifierImpl<>(newValidator));
	}

	@Override
	public MultimapVerifier<K, V> keySet(Consumer<CollectionVerifier<Set<K>, K>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(keySet());
		return this;
	}

	@Override
	public CollectionVerifier<Collection<V>, V> values()
	{
		CollectionValidator<Collection<V>, V> newValidator = validator.values();
		return validationResult(() -> new CollectionVerifierImpl<>(newValidator));
	}

	@Override
	public MultimapVerifier<K, V> values(Consumer<CollectionVerifier<Collection<V>, V>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(values());
		return this;
	}

	@Override
	public CollectionVerifier<Collection<Entry<K, V>>, Entry<K, V>> entries()
	{
		CollectionValidator<Collection<Entry<K, V>>, Entry<K, V>> newValidator = validator.entries();
		return validationResult(() -> new CollectionVerifierImpl<>(newValidator));
	}

	@Override
	public MultimapVerifier<K, V> entries(
		Consumer<CollectionVerifier<Collection<Entry<K, V>>, Entry<K, V>>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(entries());
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isEmpty()
	{
		validator.isEmpty();
		return validationResult();
	}

	@Override
	public MultimapVerifier<K, V> isNotEmpty()
	{
		validator.isNotEmpty();
		return validationResult();
	}

	@Override
	public SizeVerifier size()
	{
		SizeValidator newValidator = validator.size();
		return validationResult(() -> new SizeVerifierImpl(newValidator));
	}

	@Override
	public MultimapVerifier<K, V> size(Consumer<SizeVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(size());
		return this;
	}
}
