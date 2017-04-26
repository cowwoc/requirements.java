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
	public <C extends Collection<E>, E> CollectionVerifier<C, E> requireThat(C actual, String name)
	{
		verifyName(name);
		return new CollectionVerifierImpl<>(scope, actual, name, Pluralizer.ELEMENT, config);
	}

	@Override
	public <C extends Collection<E>, E> CollectionVerifier<C, E> assertThat(C actual, String name)
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
	public PrimitiveBooleanVerifier requireThat(boolean actual, String name)
	{
		verifyName(name);
		return new PrimitiveBooleanVerifierImpl(scope, actual, name, config);
	}

	@Override
	public PrimitiveBooleanVerifier assertThat(boolean actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveBooleanVerifier(config);
	}

	@Override
	public PrimitiveNumberVerifier<Byte> requireThat(byte actual, String name)
	{
		verifyName(name);
		return new PrimitiveNumberVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public PrimitiveNumberVerifier<Byte> assertThat(byte actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public PrimitiveNumberVerifier<Short> requireThat(short actual, String name)
	{
		verifyName(name);
		return new PrimitiveNumberVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public PrimitiveNumberVerifier<Short> assertThat(short actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public PrimitiveNumberVerifier<Integer> requireThat(int actual, String name)
	{
		verifyName(name);
		return new PrimitiveNumberVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public PrimitiveNumberVerifier<Integer> assertThat(int actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public PrimitiveNumberVerifier<Long> requireThat(long actual, String name)
	{
		verifyName(name);
		return new PrimitiveNumberVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public PrimitiveNumberVerifier<Long> assertThat(long actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Float> requireThat(float actual, String name)
	{
		verifyName(name);
		return new PrimitiveFloatVerifierImpl(scope, actual, name, config);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Float> assertThat(float actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveFloatingPointVerifier<>(config);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Double> requireThat(double actual, String name)
	{
		verifyName(name);
		return new PrimitiveDoubleVerifierImpl(scope, actual, name, config);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Double> assertThat(double actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveFloatingPointVerifier<>(config);
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
	public BooleanVerifier requireThat(Boolean actual, String name)
	{
		verifyName(name);
		return new BooleanVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public BooleanVerifier assertThat(Boolean actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpBooleanVerifier(config);
	}

	@Override
	public FloatingPointVerifier<Float> requireThat(Float actual, String name)
	{
		verifyName(name);
		return new FloatVerifierImpl(scope, actual, name, config);
	}

	@Override
	public FloatingPointVerifier<Float> assertThat(Float actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpFloatingPointVerifier<>(config);
	}

	@Override
	public FloatingPointVerifier<Double> requireThat(Double actual, String name)
	{
		verifyName(name);
		return new DoubleVerifierImpl(scope, actual, name, config);
	}

	@Override
	public FloatingPointVerifier<Double> assertThat(Double actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpFloatingPointVerifier<>(config);
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
