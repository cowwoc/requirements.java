/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava.internal;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;
import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.SizeVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.CollectionVerifierNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.SizeVerifierNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A {@code MultimapVerifier} that does nothing.
 *
 * @param <K> the type of keys in the multimap
 * @param <V> the type of values in the multimap
 */
public final class MultimapVerifierNoOp<K, V>
	extends AbstractObjectVerifierNoOp<MultimapVerifier<K, V>, Multimap<K, V>>
	implements MultimapVerifier<K, V>
{
	private static final MultimapVerifierNoOp<?, ?> INSTANCE = new MultimapVerifierNoOp<>();

	/**
	 * @param <K> the type of keys in the multimap
	 * @param <V> the type of values in the multimap
	 * @return the singleton instance
	 */
	public static <K, V> MultimapVerifierNoOp<K, V> getInstance()
	{
		@SuppressWarnings("unchecked")
		MultimapVerifierNoOp<K, V> result = (MultimapVerifierNoOp<K, V>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private MultimapVerifierNoOp()
	{
	}

	@Override
	protected MultimapVerifier<K, V> getThis()
	{
		return this;
	}

	@Override
	public CollectionVerifier<Set<K>, K> keySet()
	{
		return CollectionVerifierNoOp.getInstance();
	}

	@Override
	public MultimapVerifier<K, V> keySet(Consumer<CollectionVerifier<Set<K>, K>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public CollectionVerifier<Collection<V>, V> values()
	{
		return CollectionVerifierNoOp.getInstance();
	}

	@Override
	public MultimapVerifier<K, V> values(Consumer<CollectionVerifier<Collection<V>, V>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public CollectionVerifier<Collection<Entry<K, V>>, Entry<K, V>> entries()
	{
		return CollectionVerifierNoOp.getInstance();
	}

	@Override
	public MultimapVerifier<K, V> entries(Consumer<CollectionVerifier<Collection<Entry<K, V>>, Entry<K, V>>>
		                                      consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isEmpty()
	{
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isNotEmpty()
	{
		return this;
	}

	@Override
	public SizeVerifier size()
	{
		return SizeVerifierNoOp.getInstance();
	}

	@Override
	public MultimapVerifier<K, V> size(Consumer<SizeVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
