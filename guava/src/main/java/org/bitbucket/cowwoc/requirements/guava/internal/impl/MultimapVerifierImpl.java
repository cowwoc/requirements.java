/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava.internal.impl;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;
import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.impl.CollectionVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.impl.ContainerSizeVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.impl.ObjectCapabilitiesImpl;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.SecretConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.SharedSecrets;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Default implementation of MultimapRequirements.
 *
 * @param <K> the type of key in the multimap
 * @param <V> the type of value in the multimap
 */
public final class MultimapVerifierImpl<K, V>
	extends ObjectCapabilitiesImpl<MultimapVerifier<K, V>, Multimap<K, V>>
	implements MultimapVerifier<K, V>
{
	private final SecretConfiguration secretConfiguration = SharedSecrets.INSTANCE.secretConfiguration;

	/**
	 * @param scope  the application configuration
	 * @param name   the name of the parameter
	 * @param actual the actual value of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if {@code name} is empty
	 */
	public MultimapVerifierImpl(ApplicationScope scope, String name, Multimap<K, V> actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public CollectionVerifier<Set<K>, K> keySet()
	{
		return new CollectionVerifierImpl<>(scope, name + ".keySet()", actual.keySet(), Pluralizer.KEY, config);
	}

	@Override
	public MultimapVerifier<K, V> keySet(Consumer<CollectionVerifier<Set<K>, K>> consumer)
	{
		consumer.accept(keySet());
		return this;
	}

	@Override
	public CollectionVerifier<Collection<V>, V> values()
	{
		return new CollectionVerifierImpl<>(scope, name + ".values()", actual.values(), Pluralizer.VALUE, config);
	}

	@Override
	public MultimapVerifier<K, V> values(Consumer<CollectionVerifier<Collection<V>, V>> consumer)
	{
		consumer.accept(values());
		return this;
	}

	@Override
	public CollectionVerifier<Collection<Entry<K, V>>, Entry<K, V>> entries()
	{
		return new CollectionVerifierImpl<>(scope, name + ".entries()", actual.entries(), Pluralizer.ENTRY, config);
	}

	@Override
	public MultimapVerifier<K, V> entries(
		Consumer<CollectionVerifier<Collection<Entry<K, V>>, Entry<K, V>>> consumer)
	{
		consumer.accept(entries());
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isEmpty()
	{
		if (actual.isEmpty())
			return this;
		String actualAsString = secretConfiguration.toString(config, actual);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", actualAsString).
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
		return new ContainerSizeVerifierImpl(scope, name, actual, name + ".size()", actual.size(), Pluralizer.ENTRY, config);
	}

	@Override
	public MultimapVerifier<K, V> size(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		consumer.accept(size());
		return this;
	}
}