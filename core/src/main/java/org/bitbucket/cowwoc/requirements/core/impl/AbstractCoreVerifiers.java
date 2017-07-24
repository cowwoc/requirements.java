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
import org.bitbucket.cowwoc.requirements.core.BooleanVerifier;
import org.bitbucket.cowwoc.requirements.core.ClassVerifier;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.ComparableVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.CoreVerifiers;
import org.bitbucket.cowwoc.requirements.core.FloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.core.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.MapVerifier;
import org.bitbucket.cowwoc.requirements.core.NumberVerifier;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.OptionalVerifier;
import org.bitbucket.cowwoc.requirements.core.PathVerifier;
import org.bitbucket.cowwoc.requirements.core.PrimitiveBooleanVerifier;
import org.bitbucket.cowwoc.requirements.core.PrimitiveFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.UriVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.ArrayVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.BigDecimalVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.BooleanVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.ClassVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.CollectionVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.ComparableVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.DoubleVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.FloatVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.InetAddressVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.MapVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpArrayVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpBigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpBooleanVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpClassVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpCollectionVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpComparableVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpInetAddressVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpMapVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpNumberVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpObjectVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpOptionalVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpPathVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpPrimitiveBooleanVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpPrimitiveFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpPrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpStringVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpUriVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NumberVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.ObjectVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.OptionalVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.PathVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.Pluralizer;
import org.bitbucket.cowwoc.requirements.internal.core.impl.PrimitiveBooleanVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.PrimitiveDoubleVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.PrimitiveFloatVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.PrimitiveNumberVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.StringVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.impl.UriVerifierImpl;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * Default implementation of {@link CoreVerifiers}.
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
	public <T> ObjectVerifier<T> requireThat(String name, T actual)
	{
		verifyName(name);
		return new ObjectVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public <T> ObjectVerifier<T> assertThat(String name, T actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpObjectVerifier<>(config);
	}

	@Override
	public <C extends Collection<E>, E> CollectionVerifier<C, E> requireThat(String name, C actual)
	{
		verifyName(name);
		return new CollectionVerifierImpl<>(scope, name, actual, Pluralizer.ELEMENT, config);
	}

	@Override
	public <C extends Collection<E>, E> CollectionVerifier<C, E> assertThat(String name, C actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public <E> ArrayVerifier<E> requireThat(String name, E[] actual)
	{
		verifyName(name);
		return new ArrayVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public <E> ArrayVerifier<E> assertThat(String name, E[] actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpArrayVerifier<>(config);
	}

	@Override
	public <T extends Comparable<? super T>> ComparableVerifier<T> requireThat(String name, T actual)
	{
		verifyName(name);
		return new ComparableVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public <T extends Comparable<? super T>> ComparableVerifier<T> assertThat(String name, T actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpComparableVerifier<>(config);
	}

	@Override
	public PrimitiveBooleanVerifier requireThat(String name, boolean actual)
	{
		verifyName(name);
		return new PrimitiveBooleanVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveBooleanVerifier assertThat(String name, boolean actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpPrimitiveBooleanVerifier(config);
	}

	@Override
	public PrimitiveNumberVerifier<Byte> requireThat(String name, byte actual)
	{
		verifyName(name);
		return new PrimitiveNumberVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public PrimitiveNumberVerifier<Byte> assertThat(String name, byte actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public PrimitiveNumberVerifier<Short> requireThat(String name, short actual)
	{
		verifyName(name);
		return new PrimitiveNumberVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public PrimitiveNumberVerifier<Short> assertThat(String name, short actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public PrimitiveNumberVerifier<Integer> requireThat(String name, int actual)
	{
		verifyName(name);
		return new PrimitiveNumberVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public PrimitiveNumberVerifier<Integer> assertThat(String name, int actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public PrimitiveNumberVerifier<Long> requireThat(String name, long actual)
	{
		verifyName(name);
		return new PrimitiveNumberVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public PrimitiveNumberVerifier<Long> assertThat(String name, long actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Float> requireThat(String name, float actual)
	{
		verifyName(name);
		return new PrimitiveFloatVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Float> assertThat(String name, float actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpPrimitiveFloatingPointVerifier<>(config);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Double> requireThat(String name, double actual)
	{
		verifyName(name);
		return new PrimitiveDoubleVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Double> assertThat(String name, double actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpPrimitiveFloatingPointVerifier<>(config);
	}

	@Override
	public <T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(String name,
		T actual)
	{
		verifyName(name);
		return new NumberVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public <T extends Number & Comparable<? super T>> NumberVerifier<T> assertThat(String name,
		T actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpNumberVerifier<>(config);
	}

	@Override
	public BooleanVerifier requireThat(String name, Boolean actual)
	{
		verifyName(name);
		return new BooleanVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public BooleanVerifier assertThat(String name, Boolean actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpBooleanVerifier(config);
	}

	@Override
	public FloatingPointVerifier<Float> requireThat(String name, Float actual)
	{
		verifyName(name);
		return new FloatVerifierImpl(scope, name, actual, config);
	}

	@Override
	public FloatingPointVerifier<Float> assertThat(String name, Float actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpFloatingPointVerifier<>(config);
	}

	@Override
	public FloatingPointVerifier<Double> requireThat(String name, Double actual)
	{
		verifyName(name);
		return new DoubleVerifierImpl(scope, name, actual, config);
	}

	@Override
	public FloatingPointVerifier<Double> assertThat(String name, Double actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpFloatingPointVerifier<>(config);
	}

	@Override
	public BigDecimalVerifier requireThat(String name, BigDecimal actual)
	{
		verifyName(name);
		return new BigDecimalVerifierImpl(scope, name, actual, config);
	}

	@Override
	public BigDecimalVerifier assertThat(String name, BigDecimal actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpBigDecimalVerifier(config);
	}

	@Override
	public <K, V> MapVerifier<K, V> requireThat(String name, Map<K, V> actual)
	{
		verifyName(name);
		return new MapVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public <K, V> MapVerifier<K, V> assertThat(String name, Map<K, V> actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpMapVerifier<>(config);
	}

	@Override
	public PathVerifier requireThat(String name, Path actual)
	{
		verifyName(name);
		return new PathVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PathVerifier assertThat(String name, Path actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpPathVerifier(config);
	}

	@Override
	public StringVerifier requireThat(String name, String actual)
	{
		verifyName(name);
		return new StringVerifierImpl(scope, name, actual, config);
	}

	@Override
	public StringVerifier assertThat(String name, String actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpStringVerifier(config);
	}

	@Override
	public UriVerifier requireThat(String name, URI actual)
	{
		verifyName(name);
		return new UriVerifierImpl(scope, name, actual, config);
	}

	@Override
	public UriVerifier assertThat(String name, URI actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpUriVerifier(config);
	}

	@Override
	public <T> ClassVerifier<T> requireThat(String name, Class<T> actual)
	{
		verifyName(name);
		return new ClassVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public <T> ClassVerifier<T> assertThat(String name, Class<T> actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpClassVerifier<>(config);
	}

	@Override
	public OptionalVerifier requireThat(String name, Optional<?> actual)
	{
		verifyName(name);
		return new OptionalVerifierImpl(scope, name, actual, config);
	}

	@Override
	public OptionalVerifier assertThat(String name, Optional<?> actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpOptionalVerifier(config);
	}

	@Override
	public InetAddressVerifier requireThat(String name, InetAddress actual)
	{
		verifyName(name);
		return new InetAddressVerifierImpl(scope, name, actual, config);
	}

	@Override
	public InetAddressVerifier assertThat(String name, InetAddress actual)
	{
		if (config.assertionsAreEnabled())
			return requireThat(name, actual);
		return new NoOpInetAddressVerifier(config);
	}
}
