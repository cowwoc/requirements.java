/*
 * Copyright (c) 2013 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.BigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.java.BooleanVerifier;
import org.bitbucket.cowwoc.requirements.java.ClassVerifier;
import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.ComparableVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.FloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.GlobalConfigurable;
import org.bitbucket.cowwoc.requirements.java.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.java.IntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.JavaVerifier;
import org.bitbucket.cowwoc.requirements.java.MapVerifier;
import org.bitbucket.cowwoc.requirements.java.NumberVerifier;
import org.bitbucket.cowwoc.requirements.java.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.java.OptionalVerifier;
import org.bitbucket.cowwoc.requirements.java.PathVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveByteArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveDoubleArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveLongArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveShortArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.StringVerifier;
import org.bitbucket.cowwoc.requirements.java.UriVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.MainApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Default implementation of {@link JavaVerifier}.
 */
public final class DefaultJavaVerifier implements JavaVerifier
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
	 * Equivalent to {@link #DefaultJavaVerifier(ApplicationScope) DefaultJavaVerifier(MainApplicationScope.INSTANCE)}.
	 */
	public DefaultJavaVerifier()
	{
		this(MainApplicationScope.INSTANCE);
	}

	/**
	 * Equivalent to {@link #DefaultJavaVerifier(ApplicationScope, Configuration) DefaultJavaVerifier(scope, scope.getGlobalConfiguration())}.
	 * This constructor is meant to be used by internal classes, not by users.
	 *
	 * @param scope the application configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	public DefaultJavaVerifier(ApplicationScope scope)
	{
		assert (scope != null) : "scope may not be null";
		this.scope = scope;
		this.config = scope.getDefaultConfiguration().get();
	}

	/**
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	private DefaultJavaVerifier(ApplicationScope scope, Configuration config)
	{
		assert (scope != null) : "scope may not be null";
		assert (config != null) : "config may not be null";
		this.scope = scope;
		this.config = config;
	}

	@Override
	public boolean assertionsAreEnabled()
	{
		return config.assertionsAreEnabled();
	}

	@Override
	public JavaVerifier addContext(String name, Object value)
	{
		Configuration newConfig = config.addContext(name, value);
		return new DefaultJavaVerifier(scope, newConfig);
	}

	@Override
	public JavaVerifier withDefaultException()
	{
		Configuration newConfig = config.withAssertionsEnabled();
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaVerifier(scope, newConfig);
	}

	@Override
	public JavaVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaVerifier(scope, newConfig);
	}

	@Override
	public JavaVerifier withAssertionsDisabled()
	{
		Configuration newConfig = config.withAssertionsDisabled();
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaVerifier(scope, newConfig);
	}

	@Override
	public JavaVerifier withAssertionsEnabled()
	{
		Configuration newConfig = config.withAssertionsEnabled();
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaVerifier(scope, newConfig);
	}

	@Override
	public JavaVerifier withDiff()
	{
		Configuration newConfig = config.withDiff();
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaVerifier(scope, newConfig);
	}

	@Override
	public JavaVerifier withoutDiff()
	{
		Configuration newConfig = config.withoutDiff();
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaVerifier(scope, newConfig);
	}

	@Override
	public <T> JavaVerifier withStringConverter(Class<T> type, Function<T, String> converter)
	{
		Configuration newConfig = config.withStringConverter(type, converter);
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaVerifier(scope, newConfig);
	}

	@Override
	public <T> JavaVerifier withoutStringConverter(Class<T> type)
	{
		Configuration newConfig = config.withoutStringConverter(type);
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaVerifier(scope, newConfig);
	}

	@Override
	public Configuration getConfiguration()
	{
		return config;
	}

	@Override
	public JavaVerifier withConfiguration(Configuration configuration)
	{
		if (configuration.equals(config))
			return this;
		return new DefaultJavaVerifier(scope, configuration);
	}

	@Override
	public GlobalConfigurable getGlobalConfiguration()
	{
		return config.getGlobalConfiguration();
	}

	@Override
	public <T> ObjectVerifier<T> requireThat(T actual, String name)
	{
		verifyName(name);
		return new ObjectVerifierImpl<>(scope, name, actual, config);
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
		return new CollectionVerifierImpl<>(scope, name, actual, Pluralizer.ELEMENT, config);
	}

	@Override
	public <C extends Collection<E>, E> CollectionVerifier<C, E> assertThat(C actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public PrimitiveByteArrayVerifier requireThat(byte[] actual, String name)
	{
		verifyName(name);
		return new PrimitiveByteArrayVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveByteArrayVerifier assertThat(byte[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveByteArrayVerifier(config);
	}

	@Override
	public PrimitiveShortArrayVerifier requireThat(short[] actual, String name)
	{
		verifyName(name);
		return new PrimitiveShortArrayVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveShortArrayVerifier assertThat(short[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveShortArrayVerifier(config);
	}

	@Override
	public PrimitiveIntegerArrayVerifier requireThat(int[] actual, String name)
	{
		verifyName(name);
		return new PrimitiveIntegerArrayVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveIntegerArrayVerifier assertThat(int[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveIntegerArrayVerifier(config);
	}

	@Override
	public PrimitiveLongArrayVerifier requireThat(long[] actual, String name)
	{
		verifyName(name);
		return new PrimitiveLongArrayVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveLongArrayVerifier assertThat(long[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveLongArrayVerifier(config);
	}

	@Override
	public PrimitiveFloatArrayVerifier requireThat(float[] actual, String name)
	{
		verifyName(name);
		return new PrimitiveFloatArrayVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveFloatArrayVerifier assertThat(float[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveFloatArrayVerifier(config);
	}

	@Override
	public PrimitiveDoubleArrayVerifier requireThat(double[] actual, String name)
	{
		verifyName(name);
		return new PrimitiveDoubleArrayVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveDoubleArrayVerifier assertThat(double[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveDoubleArrayVerifier(config);
	}

	@Override
	public PrimitiveBooleanArrayVerifier requireThat(boolean[] actual, String name)
	{
		verifyName(name);
		return new PrimitiveBooleanArrayVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveBooleanArrayVerifier assertThat(boolean[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveBooleanArrayVerifier(config);
	}

	@Override
	public PrimitiveCharacterArrayVerifier requireThat(char[] actual, String name)
	{
		verifyName(name);
		return new PrimitiveCharacterArrayVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveCharacterArrayVerifier assertThat(char[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveCharacterArrayVerifier(config);
	}

	@Override
	public <E> ArrayVerifier<E> requireThat(E[] actual, String name)
	{
		verifyName(name);
		return new ArrayVerifierImpl<>(scope, name, actual, config);
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
		return new ComparableVerifierImpl<>(scope, name, actual, config);
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
		return new PrimitiveBooleanVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveBooleanVerifier assertThat(boolean actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveBooleanVerifier(config);
	}

	@Override
	public BooleanVerifier requireThat(Boolean actual, String name)
	{
		verifyName(name);
		return new BooleanVerifierImpl(scope, name, actual, config);
	}

	@Override
	public BooleanVerifier assertThat(Boolean actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpBooleanVerifier(config);
	}

	@Override
	public PrimitiveNumberVerifier<Byte> requireThat(byte actual, String name)
	{
		verifyName(name);
		return new PrimitiveNumberVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public PrimitiveNumberVerifier<Byte> assertThat(byte actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public PrimitiveCharacterVerifier requireThat(char actual, String name)
	{
		verifyName(name);
		return new PrimitiveCharacterVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveCharacterVerifier assertThat(char actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveCharacterVerifier(config);
	}

	@Override
	public PrimitiveNumberVerifier<Short> requireThat(short actual, String name)
	{
		verifyName(name);
		return new PrimitiveNumberVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public PrimitiveNumberVerifier<Short> assertThat(short actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public PrimitiveIntegerVerifier<Integer> requireThat(int actual, String name)
	{
		verifyName(name);
		return new PrimitiveIntegerVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveIntegerVerifier<Integer> assertThat(int actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveIntegerVerifier<>(config);
	}

	@Override
	public IntegerVerifier<Integer> requireThat(Integer actual, String name)
	{
		verifyName(name);
		return new IntegerVerifierImpl(scope, name, actual, config);
	}

	@Override
	public IntegerVerifier<Integer> assertThat(Integer actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpIntegerVerifier<>(config);
	}

	@Override
	public PrimitiveIntegerVerifier<Long> requireThat(long actual, String name)
	{
		verifyName(name);
		return new PrimitiveLongVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveIntegerVerifier<Long> assertThat(long actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveIntegerVerifier<>(config);
	}

	@Override
	public IntegerVerifier<Long> requireThat(Long actual, String name)
	{
		verifyName(name);
		return new LongVerifierImpl(scope, name, actual, config);
	}

	@Override
	public IntegerVerifier<Long> assertThat(Long actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpIntegerVerifier<>(config);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Float> requireThat(float actual, String name)
	{
		verifyName(name);
		return new PrimitiveFloatVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Float> assertThat(float actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveFloatingPointVerifier<>(config);
	}

	@Override
	public FloatingPointVerifier<Float> requireThat(Float actual, String name)
	{
		verifyName(name);
		return new FloatVerifierImpl(scope, name, actual, config);
	}

	@Override
	public FloatingPointVerifier<Float> assertThat(Float actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpFloatingPointVerifier<>(config);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Double> requireThat(double actual, String name)
	{
		verifyName(name);
		return new PrimitiveDoubleVerifierImpl(scope, name, actual, config);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Double> assertThat(double actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpPrimitiveFloatingPointVerifier<>(config);
	}

	@Override
	public FloatingPointVerifier<Double> requireThat(Double actual, String name)
	{
		verifyName(name);
		return new DoubleVerifierImpl(scope, name, actual, config);
	}

	@Override
	public FloatingPointVerifier<Double> assertThat(Double actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpFloatingPointVerifier<>(config);
	}

	@Override
	public <T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(T actual, String name)
	{
		verifyName(name);
		return new NumberVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public <T extends Number & Comparable<? super T>> NumberVerifier<T> assertThat(T actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpNumberVerifier<>(config);
	}

	@Override
	public BigDecimalVerifier requireThat(BigDecimal actual, String name)
	{
		verifyName(name);
		return new BigDecimalVerifierImpl(scope, name, actual, config);
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
		return new MapVerifierImpl<>(scope, name, actual, config);
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
		return new PathVerifierImpl(scope, name, actual, config);
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
		return new StringVerifierImpl(scope, name, actual, config);
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
		return new UriVerifierImpl(scope, name, actual, config);
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
		return new ClassVerifierImpl<>(scope, name, actual, config);
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
		return new OptionalVerifierImpl(scope, name, actual, config);
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
		return new InetAddressVerifierImpl(scope, name, actual, config);
	}

	@Override
	public InetAddressVerifier assertThat(InetAddress actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpInetAddressVerifier(config);
	}
}
