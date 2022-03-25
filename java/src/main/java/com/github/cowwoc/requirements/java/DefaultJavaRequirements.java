/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.internal.ArrayValidatorImpl;
import com.github.cowwoc.requirements.java.internal.ArrayVerifierImpl;
import com.github.cowwoc.requirements.java.internal.ArrayVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.BigDecimalValidatorImpl;
import com.github.cowwoc.requirements.java.internal.BigDecimalVerifierImpl;
import com.github.cowwoc.requirements.java.internal.BigDecimalVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.BooleanValidatorImpl;
import com.github.cowwoc.requirements.java.internal.BooleanVerifierImpl;
import com.github.cowwoc.requirements.java.internal.BooleanVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.ClassValidatorImpl;
import com.github.cowwoc.requirements.java.internal.ClassVerifierImpl;
import com.github.cowwoc.requirements.java.internal.ClassVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.CollectionValidatorImpl;
import com.github.cowwoc.requirements.java.internal.CollectionVerifierImpl;
import com.github.cowwoc.requirements.java.internal.CollectionVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.ComparableValidatorImpl;
import com.github.cowwoc.requirements.java.internal.ComparableVerifierImpl;
import com.github.cowwoc.requirements.java.internal.ComparableVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.DoubleValidatorImpl;
import com.github.cowwoc.requirements.java.internal.DoubleVerifierImpl;
import com.github.cowwoc.requirements.java.internal.FloatValidatorImpl;
import com.github.cowwoc.requirements.java.internal.FloatVerifierImpl;
import com.github.cowwoc.requirements.java.internal.FloatingPointVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.InetAddressValidatorImpl;
import com.github.cowwoc.requirements.java.internal.InetAddressVerifierImpl;
import com.github.cowwoc.requirements.java.internal.InetAddressVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.IntegerValidatorImpl;
import com.github.cowwoc.requirements.java.internal.IntegerVerifierImpl;
import com.github.cowwoc.requirements.java.internal.IntegerVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.ListValidatorImpl;
import com.github.cowwoc.requirements.java.internal.ListVerifierImpl;
import com.github.cowwoc.requirements.java.internal.ListVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.LongValidatorImpl;
import com.github.cowwoc.requirements.java.internal.LongVerifierImpl;
import com.github.cowwoc.requirements.java.internal.MapValidatorImpl;
import com.github.cowwoc.requirements.java.internal.MapVerifierImpl;
import com.github.cowwoc.requirements.java.internal.MapVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.NumberValidatorImpl;
import com.github.cowwoc.requirements.java.internal.NumberVerifierImpl;
import com.github.cowwoc.requirements.java.internal.NumberVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.ObjectValidatorImpl;
import com.github.cowwoc.requirements.java.internal.ObjectVerifierImpl;
import com.github.cowwoc.requirements.java.internal.ObjectVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.OptionalValidatorImpl;
import com.github.cowwoc.requirements.java.internal.OptionalVerifierImpl;
import com.github.cowwoc.requirements.java.internal.OptionalVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.PathValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PathVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PathVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.PrimitiveBooleanValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveBooleanVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveBooleanVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.PrimitiveCharacterValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveCharacterVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveCharacterVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.PrimitiveDoubleValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveDoubleVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveFloatValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveFloatVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveFloatingPointVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.PrimitiveIntegerValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveIntegerVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveIntegerVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.PrimitiveNumberValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveNumberVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveNumberVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.StringValidatorImpl;
import com.github.cowwoc.requirements.java.internal.StringVerifierImpl;
import com.github.cowwoc.requirements.java.internal.StringVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.UriValidatorImpl;
import com.github.cowwoc.requirements.java.internal.UriVerifierImpl;
import com.github.cowwoc.requirements.java.internal.UriVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.UrlValidatorImpl;
import com.github.cowwoc.requirements.java.internal.UrlVerifierImpl;
import com.github.cowwoc.requirements.java.internal.UrlVerifierNoOp;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;
import com.github.cowwoc.requirements.java.internal.secrets.JavaSecrets;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.internal.util.Objects;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.internal.util.Verifiers;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Default implementation of JavaRequirements.
 * <p>
 * This class is thread-safe.
 */
public final class DefaultJavaRequirements implements JavaRequirements
{
	static
	{
		JavaSecrets.INSTANCE.setSecretRequirements(DefaultJavaRequirements::new);
	}

	/**
	 * The application configuration.
	 */
	private final ApplicationScope scope;
	/**
	 * The instance configuration.
	 */
	private final Configuration config;

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
	private DefaultJavaRequirements(ApplicationScope scope)
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
	public boolean isCleanStackTrace()
	{
		return config.isCleanStackTrace();
	}

	@Override
	public JavaRequirements withCleanStackTrace()
	{
		Configuration newConfig = config.withCleanStackTrace();
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaRequirements(scope, newConfig);
	}

	@Override
	public JavaRequirements withoutCleanStackTrace()
	{
		Configuration newConfig = config.withoutCleanStackTrace();
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaRequirements(scope, newConfig);
	}

	@Override
	public Map<String, Object> getContext()
	{
		return config.getContext();
	}

	@Override
	@Deprecated
	public JavaRequirements putContext(String name, Object value)
	{
		return withContext(name, value);
	}

	@Override
	public JavaRequirements withContext(String name, Object value)
	{
		Configuration newConfig = config.withContext(name, value);
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaRequirements(scope, newConfig);
	}

	@Override
	@Deprecated
	public JavaRequirements removeContext(String name)
	{
		return withoutContext(name);
	}

	@Override
	public JavaRequirements withoutContext(String name)
	{
		Configuration newConfig = config.withoutContext(name);
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaRequirements(scope, newConfig);
	}

	@Override
	public JavaRequirements withAssertionsDisabled()
	{
		Configuration newConfig = config.withAssertionsDisabled();
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaRequirements(scope, newConfig);
	}

	@Override
	public JavaRequirements withAssertionsEnabled()
	{
		Configuration newConfig = config.withAssertionsEnabled();
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaRequirements(scope, newConfig);
	}

	@Override
	public boolean isDiffEnabled()
	{
		return config.isDiffEnabled();
	}

	@Override
	public JavaRequirements withDiff()
	{
		Configuration newConfig = config.withDiff();
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaRequirements(scope, newConfig);
	}

	@Override
	public JavaRequirements withoutDiff()
	{
		Configuration newConfig = config.withoutDiff();
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaRequirements(scope, newConfig);
	}

	@Override
	public String toString(Object value)
	{
		return config.toString(value);
	}

	@Override
	public <T> JavaRequirements withStringConverter(Class<T> type, Function<T, String> converter)
	{
		Configuration newConfig = config.withStringConverter(type, converter);
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaRequirements(scope, newConfig);
	}

	@Override
	public <T> JavaRequirements withoutStringConverter(Class<T> type)
	{
		Configuration newConfig = config.withoutStringConverter(type);
		if (newConfig.equals(config))
			return this;
		return new DefaultJavaRequirements(scope, newConfig);
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
		return new ObjectVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <T> ObjectVerifier<T> assertThat(T actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return ObjectVerifierNoOp.getInstance();
	}

	@Override
	public <T> ObjectValidator<T> validateThat(T actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ObjectValidatorImpl<>(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public <C extends Collection<E>, E> CollectionVerifier<C, E> requireThat(C actual, String name)
	{
		return new CollectionVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <C extends Collection<E>, E> CollectionVerifier<C, E> assertThat(C actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return CollectionVerifierNoOp.getInstance();
	}

	@Override
	public <C extends Collection<E>, E> CollectionValidator<C, E> validateThat(C actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new CollectionValidatorImpl<>(scope, config, name, actual, Pluralizer.ELEMENT, new ArrayList<>());
	}

	@Override
	public <L extends List<E>, E> ListVerifier<L, E> requireThat(L actual, String name)
	{
		return new ListVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <L extends List<E>, E> ListVerifier<L, E> assertThat(L actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return ListVerifierNoOp.getInstance();
	}

	@Override
	public <L extends List<E>, E> ListValidator<L, E> validateThat(L actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ListValidatorImpl<>(scope, config, name, actual, Pluralizer.ELEMENT, new ArrayList<>());
	}

	@Override
	public ArrayVerifier<byte[], Byte> requireThat(byte[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayVerifier<byte[], Byte> assertThat(byte[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return ArrayVerifierNoOp.getInstance();
	}

	@Override
	public ArrayValidator<byte[], Byte> validateThat(byte[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>());
	}

	@Override
	public ArrayVerifier<short[], Short> requireThat(short[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayVerifier<short[], Short> assertThat(short[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return ArrayVerifierNoOp.getInstance();
	}

	@Override
	public ArrayValidator<short[], Short> validateThat(short[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>());
	}

	@Override
	public ArrayVerifier<int[], Integer> requireThat(int[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayVerifier<int[], Integer> assertThat(int[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return ArrayVerifierNoOp.getInstance();
	}

	@Override
	public ArrayValidator<int[], Integer> validateThat(int[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>());
	}

	@Override
	public ArrayVerifier<long[], Long> requireThat(long[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayVerifier<long[], Long> assertThat(long[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return ArrayVerifierNoOp.getInstance();
	}

	@Override
	public ArrayValidator<long[], Long> validateThat(long[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>());
	}

	@Override
	public ArrayVerifier<float[], Float> requireThat(float[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayVerifier<float[], Float> assertThat(float[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return ArrayVerifierNoOp.getInstance();
	}

	@Override
	public ArrayValidator<float[], Float> validateThat(float[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>());
	}

	@Override
	public ArrayVerifier<double[], Double> requireThat(double[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayVerifier<double[], Double> assertThat(double[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return ArrayVerifierNoOp.getInstance();
	}

	@Override
	public ArrayValidator<double[], Double> validateThat(double[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>());
	}

	@Override
	public ArrayVerifier<boolean[], Boolean> requireThat(boolean[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayVerifier<boolean[], Boolean> assertThat(boolean[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return ArrayVerifierNoOp.getInstance();
	}

	@Override
	public ArrayValidator<boolean[], Boolean> validateThat(boolean[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>());
	}

	@Override
	public ArrayVerifier<char[], Character> requireThat(char[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayVerifier<char[], Character> assertThat(char[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return ArrayVerifierNoOp.getInstance();
	}

	@Override
	public ArrayValidator<char[], Character> validateThat(char[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>());
	}

	@Override
	public <E> ArrayVerifier<E[], E> requireThat(E[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <E> ArrayVerifier<E[], E> assertThat(E[] actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return ArrayVerifierNoOp.getInstance();
	}

	@Override
	public <E> ArrayValidator<E[], E> validateThat(E[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>());
	}

	@Override
	public <T extends Comparable<? super T>> ComparableVerifier<T> requireThat(T actual, String name)
	{
		return new ComparableVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <T extends Comparable<? super T>> ComparableVerifier<T> assertThat(T actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return ComparableVerifierNoOp.getInstance();
	}

	@Override
	public <T extends Comparable<? super T>> ComparableValidator<T> validateThat(T actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ComparableValidatorImpl<>(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public PrimitiveBooleanVerifier requireThat(boolean actual, String name)
	{
		return new PrimitiveBooleanVerifierImpl(validateThat(actual, name));
	}

	@Override
	public PrimitiveBooleanVerifier assertThat(boolean actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return PrimitiveBooleanVerifierNoOp.getInstance();
	}

	@Override
	public PrimitiveBooleanValidator validateThat(boolean actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveBooleanValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public BooleanVerifier requireThat(Boolean actual, String name)
	{
		return new BooleanVerifierImpl(validateThat(actual, name));
	}

	@Override
	public BooleanVerifier assertThat(Boolean actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return BooleanVerifierNoOp.getInstance();
	}

	@Override
	public BooleanValidator validateThat(Boolean actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new BooleanValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public PrimitiveNumberVerifier<Byte> requireThat(byte actual, String name)
	{
		return new PrimitiveNumberVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public PrimitiveNumberVerifier<Byte> assertThat(byte actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return PrimitiveNumberVerifierNoOp.getInstance();
	}

	@Override
	public PrimitiveNumberValidator<Byte> validateThat(byte actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveNumberValidatorImpl<>(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public PrimitiveCharacterVerifier requireThat(char actual, String name)
	{
		return new PrimitiveCharacterVerifierImpl(validateThat(actual, name));
	}

	@Override
	public PrimitiveCharacterVerifier assertThat(char actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return PrimitiveCharacterVerifierNoOp.getInstance();
	}

	@Override
	public PrimitiveCharacterValidator validateThat(char actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveCharacterValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public PrimitiveNumberVerifier<Short> requireThat(short actual, String name)
	{
		return new PrimitiveNumberVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public PrimitiveNumberVerifier<Short> assertThat(short actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return PrimitiveNumberVerifierNoOp.getInstance();
	}

	@Override
	public PrimitiveNumberValidator<Short> validateThat(short actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveNumberValidatorImpl<>(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public PrimitiveIntegerVerifier<Integer> requireThat(int actual, String name)
	{
		return new PrimitiveIntegerVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public PrimitiveIntegerVerifier<Integer> assertThat(int actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return PrimitiveIntegerVerifierNoOp.getInstance();
	}

	@Override
	public PrimitiveIntegerValidator<Integer> validateThat(int actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveIntegerValidatorImpl<>(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public IntegerVerifier<Integer> requireThat(Integer actual, String name)
	{
		return new IntegerVerifierImpl(validateThat(actual, name));
	}

	@Override
	public IntegerVerifier<Integer> assertThat(Integer actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return IntegerVerifierNoOp.getInstance();
	}

	@Override
	public IntegerValidator<Integer> validateThat(Integer actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new IntegerValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public PrimitiveIntegerVerifier<Long> requireThat(long actual, String name)
	{
		return new PrimitiveIntegerVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public PrimitiveIntegerVerifier<Long> assertThat(long actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return PrimitiveIntegerVerifierNoOp.getInstance();
	}

	@Override
	public PrimitiveIntegerValidator<Long> validateThat(long actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveIntegerValidatorImpl<>(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public IntegerVerifier<Long> requireThat(Long actual, String name)
	{
		return new LongVerifierImpl(validateThat(actual, name));
	}

	@Override
	public IntegerVerifier<Long> assertThat(Long actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return IntegerVerifierNoOp.getInstance();
	}

	@Override
	public IntegerValidator<Long> validateThat(Long actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new LongValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public PrimitiveFloatingPointVerifier<Float> requireThat(float actual, String name)
	{
		return new PrimitiveFloatVerifierImpl(validateThat(actual, name));
	}

	@Override
	public PrimitiveFloatingPointVerifier<Float> assertThat(float actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return PrimitiveFloatingPointVerifierNoOp.getInstance();
	}

	@Override
	public PrimitiveFloatingPointValidator<Float> validateThat(float actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveFloatValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public FloatingPointVerifier<Float> requireThat(Float actual, String name)
	{
		return new FloatVerifierImpl(validateThat(actual, name));
	}

	@Override
	public FloatingPointVerifier<Float> assertThat(Float actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return FloatingPointVerifierNoOp.getInstance();
	}

	@Override
	public FloatingPointValidator<Float> validateThat(Float actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new FloatValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public PrimitiveFloatingPointVerifier<Double> requireThat(double actual, String name)
	{
		return new PrimitiveDoubleVerifierImpl(validateThat(actual, name));
	}

	@Override
	public PrimitiveFloatingPointVerifier<Double> assertThat(double actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return PrimitiveFloatingPointVerifierNoOp.getInstance();
	}

	@Override
	public PrimitiveFloatingPointValidator<Double> validateThat(double actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveDoubleValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public FloatingPointVerifier<Double> requireThat(Double actual, String name)
	{
		return new DoubleVerifierImpl(validateThat(actual, name));
	}

	@Override
	public FloatingPointVerifier<Double> assertThat(Double actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return FloatingPointVerifierNoOp.getInstance();
	}

	@Override
	public FloatingPointValidator<Double> validateThat(Double actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new DoubleValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public <T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(T actual, String name)
	{
		return new NumberVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <T extends Number & Comparable<? super T>> NumberVerifier<T> assertThat(T actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return NumberVerifierNoOp.getInstance();
	}

	@Override
	public <T extends Number & Comparable<? super T>> NumberValidator<T> validateThat(T actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new NumberValidatorImpl<>(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public BigDecimalVerifier requireThat(BigDecimal actual, String name)
	{
		return new BigDecimalVerifierImpl(validateThat(actual, name));
	}

	@Override
	public BigDecimalVerifier assertThat(BigDecimal actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return BigDecimalVerifierNoOp.getInstance();
	}

	@Override
	public BigDecimalValidator validateThat(BigDecimal actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new BigDecimalValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public <K, V> MapVerifier<K, V> requireThat(Map<K, V> actual, String name)
	{
		return new MapVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <K, V> MapVerifier<K, V> assertThat(Map<K, V> actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return MapVerifierNoOp.getInstance();
	}

	@Override
	public <K, V> MapValidator<K, V> validateThat(Map<K, V> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new MapValidatorImpl<>(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public PathVerifier requireThat(Path actual, String name)
	{
		return new PathVerifierImpl(validateThat(actual, name));
	}

	@Override
	public PathVerifier assertThat(Path actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return PathVerifierNoOp.getInstance();
	}

	@Override
	public PathValidator validateThat(Path actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PathValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public StringVerifier requireThat(String actual, String name)
	{
		return new StringVerifierImpl(validateThat(actual, name));
	}

	@Override
	public StringVerifier assertThat(String actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return StringVerifierNoOp.getInstance();
	}

	@Override
	public StringValidator validateThat(String actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new StringValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public UriVerifier requireThat(URI actual, String name)
	{
		return new UriVerifierImpl(validateThat(actual, name));
	}

	@Override
	public UriVerifier assertThat(URI actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return UriVerifierNoOp.getInstance();
	}

	@Override
	public UriValidator validateThat(URI actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new UriValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public UrlVerifier requireThat(URL actual, String name)
	{
		return new UrlVerifierImpl(validateThat(actual, name));
	}

	@Override
	public UrlVerifier assertThat(URL actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return UrlVerifierNoOp.getInstance();
	}

	@Override
	public UrlValidator validateThat(URL actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new UrlValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public <T> ClassVerifier<T> requireThat(Class<T> actual, String name)
	{
		return new ClassVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <T> ClassVerifier<T> assertThat(Class<T> actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return ClassVerifierNoOp.getInstance();
	}

	@Override
	public <T> ClassValidator<T> validateThat(Class<T> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ClassValidatorImpl<>(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public OptionalVerifier requireThat(Optional<?> actual, String name)
	{
		return new OptionalVerifierImpl(validateThat(actual, name));
	}

	@Override
	public OptionalVerifier assertThat(Optional<?> actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return OptionalVerifierNoOp.getInstance();
	}

	@Override
	public OptionalValidator validateThat(Optional<?> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new OptionalValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}

	@Override
	public InetAddressVerifier requireThat(InetAddress actual, String name)
	{
		return new InetAddressVerifierImpl(validateThat(actual, name));
	}

	@Override
	public InetAddressVerifier assertThat(InetAddress actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return InetAddressVerifierNoOp.getInstance();
	}

	@Override
	public InetAddressValidator validateThat(InetAddress actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new InetAddressValidatorImpl(scope, config, name, actual, new ArrayList<>());
	}
}
