/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.bitbucket.cowwoc.requirements.core.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.core.BigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.core.ClassVerifier;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.ComparableVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.CoreVerifiers;
import org.bitbucket.cowwoc.requirements.core.DoubleVerifier;
import org.bitbucket.cowwoc.requirements.core.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.MapVerifier;
import org.bitbucket.cowwoc.requirements.core.NumberVerifier;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.OptionalVerifier;
import org.bitbucket.cowwoc.requirements.core.PathVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.UriVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;

/**
 * Default implementation of {@code CoreVerifiers}.
 *
 * @author Gili Tzabari
 */
public abstract class AbstractCoreVerifiers implements CoreVerifiers
{
	/**
	 * @param name the name of the actual value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	private static void verifyName(String name)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (name.trim().isEmpty())
			throw new IllegalArgumentException("name may not be empty");
	}
	protected final ApplicationScope scope;
	protected final Configuration config;

	/**
	 * Creates new verifiers.
	 *
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	protected AbstractCoreVerifiers(ApplicationScope scope, Configuration config)
	{
		assert (scope != null): "scope may not be null";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.config = config;
	}

	@Override
	public boolean assertionsAreEnabled()
	{
		return config.assertionsAreEnabled();
	}

	@Override
	public <T> ObjectVerifier<T> requireThat(T actual, String name)
	{
		verifyName(name);
		return new ObjectVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public <T> ObjectVerifier<T> assertThat(T actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpObjectVerifier<>(config);
	}

	@Override
	public <E> CollectionVerifier<E> requireThat(Collection<E> actual, String name)
	{
		verifyName(name);
		return new CollectionVerifierImpl<>(scope, actual, name, Pluralizer.ELEMENT, config);
	}

	@Override
	public <E> CollectionVerifier<E> assertThat(Collection<E> actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public <E> ArrayVerifier<E> requireThat(E[] actual, String name)
	{
		verifyName(name);
		return new ArrayVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public <E> ArrayVerifier<E> assertThat(E[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpArrayVerifier<>(config);
	}

	@Override
	public <T extends Comparable<? super T>> ComparableVerifier<T> requireThat(T actual, String name)
	{
		verifyName(name);
		return new ComparableVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public <T extends Comparable<? super T>> ComparableVerifier<T> assertThat(T actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpComparableVerifier<>(config);
	}

	@Override
	public <T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(T actual,
		String name)
	{
		verifyName(name);
		return new NumberVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public <T extends Number & Comparable<? super T>> NumberVerifier<T> assertThat(T actual,
		String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpNumberVerifier<>(config);
	}

	@Override
	public DoubleVerifier requireThat(Double actual, String name)
	{
		verifyName(name);
		return new DoubleVerifierImpl(scope, actual, name, config);
	}

	@Override
	public DoubleVerifier assertThat(Double actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpDoubleVerifier(config);
	}

	@Override
	public BigDecimalVerifier requireThat(BigDecimal actual, String name)
	{
		verifyName(name);
		return new BigDecimalVerifierImpl(scope, actual, name, config);
	}

	@Override
	public BigDecimalVerifier assertThat(BigDecimal actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpBigDecimalVerifier(config);
	}

	@Override
	public <K, V> MapVerifier<K, V> requireThat(Map<K, V> actual, String name)
	{
		verifyName(name);
		return new MapVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public <K, V> MapVerifier<K, V> assertThat(Map<K, V> actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpMapVerifier<>(config);
	}

	@Override
	public PathVerifier requireThat(Path actual, String name)
	{
		verifyName(name);
		return new PathVerifierImpl(scope, actual, name, config);
	}

	@Override
	public PathVerifier assertThat(Path actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPathVerifier(config);
	}

	@Override
	public StringVerifier requireThat(String actual, String name)
	{
		verifyName(name);
		return new StringVerifierImpl(scope, actual, name, config);
	}

	@Override
	public StringVerifier assertThat(String actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpStringVerifier(config);
	}

	@Override
	public UriVerifier requireThat(URI actual, String name)
	{
		verifyName(name);
		return new UriVerifierImpl(scope, actual, name, config);
	}

	@Override
	public UriVerifier assertThat(URI actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpUriVerifier(config);
	}

	@Override
	public <T> ClassVerifier<T> requireThat(Class<T> actual, String name)
	{
		verifyName(name);
		return new ClassVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public <T> ClassVerifier<T> assertThat(Class<T> actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpClassVerifier<>(config);
	}

	@Override
	public OptionalVerifier requireThat(Optional<?> actual, String name)
	{
		verifyName(name);
		return new OptionalVerifierImpl(scope, actual, name, config);
	}

	@Override
	public OptionalVerifier assertThat(Optional<?> actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpOptionalVerifier(config);
	}

	@Override
	public InetAddressVerifier requireThat(InetAddress actual, String name)
	{
		verifyName(name);
		return new InetAddressVerifierImpl(scope, actual, name, config);
	}

	@Override
	public InetAddressVerifier assertThat(InetAddress actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpInetAddressVerifier(config);
	}
}
