/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.CollectionValidator;
import org.bitbucket.cowwoc.requirements.java.MapValidator;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A {@code MapValidator} that does nothing.
 *
 * @param <K> the type of keys in the map
 * @param <V> the type of values in the map
 */
public final class MapValidatorNoOp<K, V>
	extends AbstractObjectValidatorNoOp<MapValidator<K, V>, Map<K, V>>
	implements MapValidator<K, V>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public MapValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected MapValidator<K, V> getThis()
	{
		return this;
	}

	@Override
	public CollectionValidator<Set<K>, K> keySet()
	{
		return new CollectionValidatorNoOp<>(failures);
	}

	@Override
	public MapValidator<K, V> keySet(Consumer<CollectionValidator<Set<K>, K>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public CollectionValidator<Collection<V>, V> values()
	{
		return new CollectionValidatorNoOp<>(failures);
	}

	@Override
	public MapValidator<K, V> values(Consumer<CollectionValidator<Collection<V>, V>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public CollectionValidator<Set<Entry<K, V>>, Entry<K, V>> entrySet()
	{
		return new CollectionValidatorNoOp<>(failures);
	}

	@Override
	public MapValidator<K, V> entrySet(Consumer<CollectionValidator<Set<Entry<K, V>>, Entry<K, V>>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public MapValidator<K, V> isEmpty()
	{
		return this;
	}

	@Override
	public MapValidator<K, V> isNotEmpty()
	{
		return this;
	}

	@Override
	public SizeValidator size()
	{
		return new SizeValidatorNoOp(failures);
	}

	@Override
	public MapValidator<K, V> size(Consumer<SizeValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}