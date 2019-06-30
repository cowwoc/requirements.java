/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.internal.ArrayValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.ArrayVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.BigDecimalValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.BigDecimalVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.BooleanValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.BooleanVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.ClassValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.ClassVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.CollectionValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.CollectionVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.ComparableValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.ComparableVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.DoubleValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.DoubleVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.FloatValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.FloatVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.InetAddressValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.InetAddressVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.IntegerValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.IntegerVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.LongValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.LongVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.MapValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.MapVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpBigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpBooleanVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpClassVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpCollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpComparableVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpInetAddressVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpIntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpMapVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpObjectVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpOptionalVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPathVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPrimitiveBooleanArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPrimitiveBooleanVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPrimitiveByteArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPrimitiveCharacterArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPrimitiveCharacterVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPrimitiveDoubleArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPrimitiveFloatArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPrimitiveFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPrimitiveIntegerArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPrimitiveLongArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpPrimitiveShortArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpStringVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NoOpUriVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.NumberValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.NumberVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.ObjectValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.ObjectVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.OptionalValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.OptionalVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PathValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PathVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveBooleanArrayValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveBooleanArrayVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveBooleanValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveBooleanVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveByteArrayValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveByteArrayVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveCharacterArrayValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveCharacterArrayVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveCharacterValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveCharacterVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveDoubleArrayValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveDoubleArrayVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveDoubleValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveDoubleVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveFloatArrayValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveFloatArrayVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveFloatValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveFloatVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveIntegerArrayValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveIntegerArrayVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveIntegerValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveIntegerVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveLongArrayValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveLongArrayVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveLongValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveLongVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveNumberValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveNumberVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveShortArrayValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.PrimitiveShortArrayVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.StringValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.StringVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.UriValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.UriVerifierImpl;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.MainApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;
import org.bitbucket.cowwoc.requirements.java.internal.util.Verifiers;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Default implementation of JavaRequirements.
 */
public final class DefaultJavaRequirements implements JavaRequirements
{
	/**
	 * The application configuration.
	 */
	protected final ApplicationScope scope;
	/**
	 * The instance configuration.
	 */
	protected Configuration config;

	/**
	 * Equivalent to {@link #DefaultJavaRequirements(ApplicationScope)
	 * DefaultJavaRequirements(MainApplicationScope.INSTANCE)}.
	 */
	public DefaultJavaRequirements()
	{
		this(MainApplicationScope.INSTANCE);
	}

	/**
	 * Equivalent to {@link #DefaultJavaRequirements(ApplicationScope, Configuration)
	 * DefaultJavaRequirements(scope, scope.getGlobalConfiguration())}.
	 * This constructor is meant to be used by secrets classes, not by users.
	 *
	 * @param scope the application configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	public DefaultJavaRequirements(ApplicationScope scope)
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
	private DefaultJavaRequirements(ApplicationScope scope, Configuration config)
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
	public Map<String, Object> getContext()
	{
		return config.getContext();
	}

	@Override
	public JavaRequirements putContext(String name, Object value)
	{
		config.putContext(name, value);
		return this;
	}

	@Override
	public JavaRequirements removeContext(String name)
	{
		config.removeContext(name);
		return this;
	}

	@Override
	public JavaRequirements withAssertionsDisabled()
	{
		config.withAssertionsDisabled();
		return this;
	}

	@Override
	public JavaRequirements withAssertionsEnabled()
	{
		config.withAssertionsEnabled();
		return this;
	}

	@Override
	public boolean isDiffEnabled()
	{
		return config.isDiffEnabled();
	}

	@Override
	public JavaRequirements withDiff()
	{
		config.withDiff();
		return this;
	}

	@Override
	public JavaRequirements withoutDiff()
	{
		config.withoutDiff();
		return this;
	}

	@Override
	public String toString(Object o)
	{
		return config.toString(o);
	}

	@Override
	public <T> JavaRequirements withStringConverter(Class<T> type, Function<T, String> converter)
	{
		config.withStringConverter(type, converter);
		return this;
	}

	@Override
	public <T> JavaRequirements withoutStringConverter(Class<T> type)
	{
		config.withoutStringConverter(type);
		return this;
	}

	@Override
	public JavaRequirements withConfiguration(Configuration configuration)
	{
		if (configuration.equals(config))
			return this;
		return new DefaultJavaRequirements(scope, configuration);
	}

	@Override
	public <T> ObjectVerifier<T> requireThat(T actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public <T> ObjectValidator<T> validateThat(T actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ObjectValidatorImpl<>(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public <C extends Collection<E>, E> CollectionVerifier<C, E> requireThat(C actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public <C extends Collection<E>, E> CollectionValidator<C, E> validateThat(C actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new CollectionValidatorImpl<>(scope, name, actual, Pluralizer.ELEMENT, config, new ArrayList<>());
	}

	@Override
	public PrimitiveByteArrayVerifier requireThat(byte[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveByteArrayValidator validateThat(byte[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveByteArrayValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveShortArrayVerifier requireThat(short[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveShortArrayValidator validateThat(short[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveShortArrayValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveIntegerArrayVerifier requireThat(int[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveIntegerArrayValidator validateThat(int[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveIntegerArrayValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveLongArrayVerifier requireThat(long[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveLongArrayValidator validateThat(long[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveLongArrayValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveFloatArrayVerifier requireThat(float[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveFloatArrayValidator validateThat(float[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveFloatArrayValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveDoubleArrayVerifier requireThat(double[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveDoubleArrayValidator validateThat(double[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveDoubleArrayValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveBooleanArrayVerifier requireThat(boolean[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveBooleanArrayValidator validateThat(boolean[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveBooleanArrayValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveCharacterArrayVerifier requireThat(char[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveCharacterArrayValidator validateThat(char[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveCharacterArrayValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public <E> ArrayVerifier<E> requireThat(E[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public <E> ArrayValidator<E> validateThat(E[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public <T extends Comparable<? super T>> ComparableVerifier<T> requireThat(T actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public <T extends Comparable<? super T>> ComparableValidator<T> validateThat(T actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ComparableValidatorImpl<>(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveBooleanVerifier requireThat(boolean actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveBooleanValidator validateThat(boolean actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveBooleanValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public BooleanVerifier requireThat(Boolean actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public BooleanValidator validateThat(Boolean actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new BooleanValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveNumberVerifier<Byte> requireThat(byte actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveNumberValidator<Byte> validateThat(byte actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveNumberValidatorImpl<>(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveCharacterVerifier requireThat(char actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveCharacterValidator validateThat(char actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveCharacterValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveNumberVerifier<Short> requireThat(short actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveNumberValidator<Short> validateThat(short actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveNumberValidatorImpl<>(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveIntegerVerifier<Integer> requireThat(int actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveIntegerValidator<Integer> validateThat(int actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveIntegerValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public IntegerVerifier<Integer> requireThat(Integer actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public IntegerValidator<Integer> validateThat(Integer actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new IntegerValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveIntegerVerifier<Long> requireThat(long actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveIntegerValidator<Long> validateThat(long actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveLongValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public IntegerVerifier<Long> requireThat(Long actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public IntegerValidator<Long> validateThat(Long actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new LongValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveFloatingPointVerifier<Float> requireThat(float actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveFloatingPointValidator<Float> validateThat(float actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveFloatValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public FloatingPointVerifier<Float> requireThat(Float actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public FloatingPointValidator<Float> validateThat(Float actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new FloatValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PrimitiveFloatingPointVerifier<Double> requireThat(double actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PrimitiveFloatingPointValidator<Double> validateThat(double actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveDoubleValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public FloatingPointVerifier<Double> requireThat(Double actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public FloatingPointValidator<Double> validateThat(Double actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new DoubleValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public <T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(T actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public <T extends Number & Comparable<? super T>> NumberValidator<T> validateThat(T actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new NumberValidatorImpl<>(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public BigDecimalVerifier requireThat(BigDecimal actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public BigDecimalValidator validateThat(BigDecimal actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new BigDecimalValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public <K, V> MapVerifier<K, V> requireThat(Map<K, V> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public <K, V> MapValidator<K, V> validateThat(Map<K, V> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new MapValidatorImpl<>(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public PathVerifier requireThat(Path actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public PathValidator validateThat(Path actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PathValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public StringVerifier requireThat(String actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public StringValidator validateThat(String actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new StringValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public UriVerifier requireThat(URI actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public UriValidator validateThat(URI actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new UriValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public <T> ClassVerifier<T> requireThat(Class<T> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public <T> ClassValidator<T> validateThat(Class<T> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ClassValidatorImpl<>(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public OptionalVerifier requireThat(Optional<?> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
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
	public OptionalValidator validateThat(Optional<?> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new OptionalValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public InetAddressVerifier requireThat(InetAddress actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new InetAddressVerifierImpl(scope, name, actual, config);
	}

	@Override
	public InetAddressVerifier assertThat(InetAddress actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpInetAddressVerifier(config);
	}

	@Override
	public InetAddressValidator validateThat(InetAddress actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new InetAddressValidatorImpl(scope, name, actual, config, new ArrayList<>());
	}
}
