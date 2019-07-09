/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.CollectionValidator;
import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.MapValidator;
import org.bitbucket.cowwoc.requirements.java.MapVerifier;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.SizeVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Default implementation of {@code MapVerifier}.
 *
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 */
public final class MapVerifierImpl<K, V>
	extends AbstractObjectVerifier<MapVerifier<K, V>, MapValidator<K, V>, Map<K, V>>
	implements MapVerifier<K, V>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public MapVerifierImpl(MapValidator<K, V> validator)
	{
		super(validator);
	}

	@Override
	protected MapVerifier<K, V> getThis()
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
	public MapVerifier<K, V> keySet(Consumer<CollectionVerifier<Set<K>, K>> consumer)
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
	public MapVerifier<K, V> values(Consumer<CollectionVerifier<Collection<V>, V>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(values());
		return this;
	}

	@Override
	public CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>> entrySet()
	{
		CollectionValidator<Set<Entry<K, V>>, Entry<K, V>> newValidator = validator.entrySet();
		return validationResult(() -> new CollectionVerifierImpl<>(newValidator));
	}

	@Override
	public MapVerifier<K, V> entrySet(Consumer<CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(entrySet());
		return this;
	}

	@Override
	public MapVerifier<K, V> isEmpty()
	{
		validator.isEmpty();
		return validationResult();
	}

	@Override
	public MapVerifier<K, V> isNotEmpty()
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
	public MapVerifier<K, V> size(Consumer<SizeVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(size());
		return this;
	}
}
