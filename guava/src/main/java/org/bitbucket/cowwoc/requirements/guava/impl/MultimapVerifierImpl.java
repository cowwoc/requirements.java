/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava.impl;

import com.google.common.collect.Multimap;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.CollectionVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.ContainerSizeVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.ObjectCapabilitiesImpl;
import org.bitbucket.cowwoc.requirements.core.impl.Pluralizer;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;

/**
 * Default implementation of MultimapRequirements.
 *
 * @param <K> the type of key in the multimap
 * @param <V> the type of value in the multimap
 * @author Gili Tzabari
 */
public final class MultimapVerifierImpl<K, V>
	extends ObjectCapabilitiesImpl<MultimapVerifier<K, V>, Multimap<K, V>>
	implements MultimapVerifier<K, V>
{
	/**
	 * Creates new MultimapRequirementsImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public MultimapVerifierImpl(ApplicationScope scope, Multimap<K, V> actual, String name,
		Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Override
	public CollectionVerifier<K> keySet()
	{
		return new CollectionVerifierImpl<>(scope, actual.keySet(), name + ".keySet()", Pluralizer.KEY,
			config);
	}

	@Override
	public MultimapVerifier<K, V> keySet(Consumer<CollectionVerifier<K>> consumer)
	{
		consumer.accept(keySet());
		return this;
	}

	@Override
	public CollectionVerifier<V> values()
	{
		return new CollectionVerifierImpl<>(scope, actual.values(), name + ".values()", Pluralizer.VALUE,
			config);
	}

	@Override
	public MultimapVerifier<K, V> values(Consumer<CollectionVerifier<V>> consumer)
	{
		consumer.accept(values());
		return this;
	}

	@Override
	public CollectionVerifier<Entry<K, V>> entries()
	{
		return new CollectionVerifierImpl<>(scope, actual.entries(), name + ".entries()",
			Pluralizer.ENTRY, config);
	}

	@Override
	public MultimapVerifier<K, V> entries(Consumer<CollectionVerifier<Entry<K, V>>> consumer)
	{
		consumer.accept(entries());
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isEmpty()
	{
		if (actual.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public MultimapVerifier<K, V> isNotEmpty()
	{
		if (!actual.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be empty", name)).
			build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> size()
	{
		return new ContainerSizeVerifierImpl(scope, actual, actual.size(), name, name + ".size()",
			Pluralizer.ENTRY, config);
	}

	@Override
	public MultimapVerifier<K, V> size(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		consumer.accept(size());
		return this;
	}
}
