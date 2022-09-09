/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.internal.ArrayValidatorImpl;
import com.github.cowwoc.requirements.java.internal.ArrayVerifierImpl;
import com.github.cowwoc.requirements.java.internal.BigDecimalValidatorImpl;
import com.github.cowwoc.requirements.java.internal.BigDecimalVerifierImpl;
import com.github.cowwoc.requirements.java.internal.BooleanValidatorImpl;
import com.github.cowwoc.requirements.java.internal.BooleanVerifierImpl;
import com.github.cowwoc.requirements.java.internal.ClassValidatorImpl;
import com.github.cowwoc.requirements.java.internal.ClassVerifierImpl;
import com.github.cowwoc.requirements.java.internal.CollectionValidatorImpl;
import com.github.cowwoc.requirements.java.internal.CollectionVerifierImpl;
import com.github.cowwoc.requirements.java.internal.ComparableValidatorImpl;
import com.github.cowwoc.requirements.java.internal.ComparableVerifierImpl;
import com.github.cowwoc.requirements.java.internal.DoubleValidatorImpl;
import com.github.cowwoc.requirements.java.internal.DoubleVerifierImpl;
import com.github.cowwoc.requirements.java.internal.FloatValidatorImpl;
import com.github.cowwoc.requirements.java.internal.FloatVerifierImpl;
import com.github.cowwoc.requirements.java.internal.InetAddressValidatorImpl;
import com.github.cowwoc.requirements.java.internal.InetAddressVerifierImpl;
import com.github.cowwoc.requirements.java.internal.IntegerValidatorImpl;
import com.github.cowwoc.requirements.java.internal.IntegerVerifierImpl;
import com.github.cowwoc.requirements.java.internal.ListValidatorImpl;
import com.github.cowwoc.requirements.java.internal.ListVerifierImpl;
import com.github.cowwoc.requirements.java.internal.LongValidatorImpl;
import com.github.cowwoc.requirements.java.internal.LongVerifierImpl;
import com.github.cowwoc.requirements.java.internal.MapValidatorImpl;
import com.github.cowwoc.requirements.java.internal.MapVerifierImpl;
import com.github.cowwoc.requirements.java.internal.NumberValidatorImpl;
import com.github.cowwoc.requirements.java.internal.NumberVerifierImpl;
import com.github.cowwoc.requirements.java.internal.ObjectValidatorImpl;
import com.github.cowwoc.requirements.java.internal.ObjectVerifierImpl;
import com.github.cowwoc.requirements.java.internal.OptionalValidatorImpl;
import com.github.cowwoc.requirements.java.internal.OptionalVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PathValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PathVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveBooleanValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveBooleanVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveCharacterValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveCharacterVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveDoubleValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveDoubleVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveFloatValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveFloatVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveIntegerValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveIntegerVerifierImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveNumberValidatorImpl;
import com.github.cowwoc.requirements.java.internal.PrimitiveNumberVerifierImpl;
import com.github.cowwoc.requirements.java.internal.StringValidatorImpl;
import com.github.cowwoc.requirements.java.internal.StringVerifierImpl;
import com.github.cowwoc.requirements.java.internal.UriValidatorImpl;
import com.github.cowwoc.requirements.java.internal.UriVerifierImpl;
import com.github.cowwoc.requirements.java.internal.UrlValidatorImpl;
import com.github.cowwoc.requirements.java.internal.UrlVerifierImpl;
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
	private Configuration config;

	/**
	 * Equivalent to {@link #DefaultJavaRequirements(ApplicationScope)
	 * DefaultJavaRequirements(MainApplicationScope.INSTANCE)}.
	 */
	public DefaultJavaRequirements()
	{
		this(MainApplicationScope.INSTANCE);
	}

	/**
	 * This constructor is meant to be used by secrets classes, not by users.
	 *
	 * @param scope the application configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	private DefaultJavaRequirements(ApplicationScope scope)
	{
		this(scope, scope.getDefaultConfiguration().get());
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
	public JavaRequirements copy()
	{
		return new DefaultJavaRequirements(scope, config.copy());
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
		config.withCleanStackTrace();
		return this;
	}

	@Override
	public JavaRequirements withoutCleanStackTrace()
	{
		config.withoutCleanStackTrace();
		return this;
	}

	@Override
	public Map<String, Object> getContext()
	{
		return config.getContext();
	}

	@Override
	public JavaRequirements withContext(String name, Object value)
	{
		config.withContext(name, value);
		return this;
	}

	@Override
	public JavaRequirements withoutContext(String name)
	{
		config.withoutContext(name);
		return this;
	}

	@Override
	public JavaRequirements withoutAnyContext()
	{
		config.withoutAnyContext();
		return this;
	}

	@Override
	public String getContextMessage(String message)
	{
		return scope.getExceptions().getContextMessage(scope.getThreadConfiguration().get().getContext(), this,
			message, List.of());
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
	public String toString(Object value)
	{
		return config.toString(value);
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
		this.config = configuration;
		return this;
	}

	@Override
	public <T> ObjectVerifier<T> requireThat(T actual, String name)
	{
		return new ObjectVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <T> ObjectValidator<T> validateThat(T actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ObjectValidatorImpl<>(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public <C extends Collection<E>, E> CollectionVerifier<C, E> requireThat(C actual, String name)
	{
		return new CollectionVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <C extends Collection<E>, E> CollectionValidator<C, E> validateThat(C actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new CollectionValidatorImpl<>(scope, config, name, actual, Pluralizer.ELEMENT, new ArrayList<>(),
			false);
	}

	@Override
	public <L extends List<E>, E> ListVerifier<L, E> requireThat(L actual, String name)
	{
		return new ListVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <L extends List<E>, E> ListValidator<L, E> validateThat(L actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ListValidatorImpl<>(scope, config, name, actual, Pluralizer.ELEMENT, new ArrayList<>(),
			false);
	}

	@Override
	public ArrayVerifier<byte[], Byte> requireThat(byte[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayValidator<byte[], Byte> validateThat(byte[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>(), false);
	}

	@Override
	public ArrayVerifier<short[], Short> requireThat(short[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayValidator<short[], Short> validateThat(short[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>(), false);
	}

	@Override
	public ArrayVerifier<int[], Integer> requireThat(int[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayValidator<int[], Integer> validateThat(int[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>(), false);
	}

	@Override
	public ArrayVerifier<long[], Long> requireThat(long[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayValidator<long[], Long> validateThat(long[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>(), false);
	}

	@Override
	public ArrayVerifier<float[], Float> requireThat(float[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayValidator<float[], Float> validateThat(float[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>(), false);
	}

	@Override
	public ArrayVerifier<double[], Double> requireThat(double[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayValidator<double[], Double> validateThat(double[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>(), false);
	}

	@Override
	public ArrayVerifier<boolean[], Boolean> requireThat(boolean[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayValidator<boolean[], Boolean> validateThat(boolean[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>(), false);
	}

	@Override
	public ArrayVerifier<char[], Character> requireThat(char[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public ArrayValidator<char[], Character> validateThat(char[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>(), false);
	}

	@Override
	public <E> ArrayVerifier<E[], E> requireThat(E[] actual, String name)
	{
		return new ArrayVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <E> ArrayValidator<E[], E> validateThat(E[] actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ArrayValidatorImpl<>(scope, config, name, actual, () -> Arrays.asList(actual),
			Objects::equals, o -> Arrays.contains(actual, o), () -> actual.length, new ArrayList<>(), false);
	}

	@Override
	public <T extends Comparable<? super T>> ComparableVerifier<T> requireThat(T actual, String name)
	{
		return new ComparableVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <T extends Comparable<? super T>> ComparableValidator<T> validateThat(T actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ComparableValidatorImpl<>(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public PrimitiveBooleanVerifier requireThat(boolean actual, String name)
	{
		return new PrimitiveBooleanVerifierImpl(validateThat(actual, name));
	}

	@Override
	public PrimitiveBooleanValidator validateThat(boolean actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveBooleanValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public BooleanVerifier requireThat(Boolean actual, String name)
	{
		return new BooleanVerifierImpl(validateThat(actual, name));
	}

	@Override
	public BooleanValidator validateThat(Boolean actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new BooleanValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public PrimitiveNumberVerifier<Byte> requireThat(byte actual, String name)
	{
		return new PrimitiveNumberVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public PrimitiveNumberValidator<Byte> validateThat(byte actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveNumberValidatorImpl<>(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public PrimitiveCharacterVerifier requireThat(char actual, String name)
	{
		return new PrimitiveCharacterVerifierImpl(validateThat(actual, name));
	}

	@Override
	public PrimitiveCharacterValidator validateThat(char actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveCharacterValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public PrimitiveNumberVerifier<Short> requireThat(short actual, String name)
	{
		return new PrimitiveNumberVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public PrimitiveNumberValidator<Short> validateThat(short actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveNumberValidatorImpl<>(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public PrimitiveIntegerVerifier<Integer> requireThat(int actual, String name)
	{
		return new PrimitiveIntegerVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public PrimitiveIntegerValidator<Integer> validateThat(int actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveIntegerValidatorImpl<>(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public IntegerVerifier<Integer> requireThat(Integer actual, String name)
	{
		return new IntegerVerifierImpl(validateThat(actual, name));
	}

	@Override
	public IntegerValidator<Integer> validateThat(Integer actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new IntegerValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public PrimitiveIntegerVerifier<Long> requireThat(long actual, String name)
	{
		return new PrimitiveIntegerVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public PrimitiveIntegerValidator<Long> validateThat(long actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveIntegerValidatorImpl<>(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public IntegerVerifier<Long> requireThat(Long actual, String name)
	{
		return new LongVerifierImpl(validateThat(actual, name));
	}

	@Override
	public IntegerValidator<Long> validateThat(Long actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new LongValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Float> requireThat(float actual, String name)
	{
		return new PrimitiveFloatVerifierImpl(validateThat(actual, name));
	}

	@Override
	public PrimitiveFloatingPointValidator<Float> validateThat(float actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveFloatValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public FloatingPointVerifier<Float> requireThat(Float actual, String name)
	{
		return new FloatVerifierImpl(validateThat(actual, name));
	}

	@Override
	public FloatingPointValidator<Float> validateThat(Float actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new FloatValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public PrimitiveFloatingPointVerifier<Double> requireThat(double actual, String name)
	{
		return new PrimitiveDoubleVerifierImpl(validateThat(actual, name));
	}

	@Override
	public PrimitiveFloatingPointValidator<Double> validateThat(double actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PrimitiveDoubleValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public FloatingPointVerifier<Double> requireThat(Double actual, String name)
	{
		return new DoubleVerifierImpl(validateThat(actual, name));
	}

	@Override
	public FloatingPointValidator<Double> validateThat(Double actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new DoubleValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public <T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(T actual, String name)
	{
		return new NumberVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <T extends Number & Comparable<? super T>> NumberValidator<T> validateThat(T actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new NumberValidatorImpl<>(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public BigDecimalVerifier requireThat(BigDecimal actual, String name)
	{
		return new BigDecimalVerifierImpl(validateThat(actual, name));
	}

	@Override
	public BigDecimalValidator validateThat(BigDecimal actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new BigDecimalValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public <K, V> MapVerifier<K, V> requireThat(Map<K, V> actual, String name)
	{
		return new MapVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <K, V> MapValidator<K, V> validateThat(Map<K, V> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new MapValidatorImpl<>(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public PathVerifier requireThat(Path actual, String name)
	{
		return new PathVerifierImpl(validateThat(actual, name));
	}

	@Override
	public PathValidator validateThat(Path actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new PathValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public StringVerifier requireThat(String actual, String name)
	{
		return new StringVerifierImpl(validateThat(actual, name));
	}

	@Override
	public StringValidator validateThat(String actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new StringValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public UriVerifier requireThat(URI actual, String name)
	{
		return new UriVerifierImpl(validateThat(actual, name));
	}

	@Override
	public UriValidator validateThat(URI actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new UriValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public UrlVerifier requireThat(URL actual, String name)
	{
		return new UrlVerifierImpl(validateThat(actual, name));
	}

	@Override
	public UrlValidator validateThat(URL actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new UrlValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public <T> ClassVerifier<T> requireThat(Class<T> actual, String name)
	{
		return new ClassVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <T> ClassValidator<T> validateThat(Class<T> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new ClassValidatorImpl<>(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public OptionalVerifier requireThat(Optional<?> actual, String name)
	{
		return new OptionalVerifierImpl(validateThat(actual, name));
	}

	@Override
	public OptionalValidator validateThat(Optional<?> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new OptionalValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}

	@Override
	public InetAddressVerifier requireThat(InetAddress actual, String name)
	{
		return new InetAddressVerifierImpl(validateThat(actual, name));
	}

	@Override
	public InetAddressValidator validateThat(InetAddress actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new InetAddressValidatorImpl(scope, config, name, actual, new ArrayList<>(), false);
	}
}