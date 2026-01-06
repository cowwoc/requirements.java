/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.test;

import com.google.common.collect.Multimap;
import io.github.cowwoc.requirements13.annotation.CheckReturnValue;
import io.github.cowwoc.requirements13.guava.internal.validator.GuavaValidatorsImpl;
import io.github.cowwoc.requirements13.guava.validator.MultimapValidator;
import io.github.cowwoc.requirements13.jackson.internal.validator.JacksonValidatorsImpl;
import io.github.cowwoc.requirements13.jackson.validator.JsonNodeValidator;
import io.github.cowwoc.requirements13.java.GenericType;
import io.github.cowwoc.requirements13.java.GlobalConfiguration;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.ConfigurationUpdater;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.java.internal.validator.JavaValidatorsImpl;
import io.github.cowwoc.requirements13.java.validator.BigDecimalValidator;
import io.github.cowwoc.requirements13.java.validator.BigIntegerValidator;
import io.github.cowwoc.requirements13.java.validator.BooleanValidator;
import io.github.cowwoc.requirements13.java.validator.ByteValidator;
import io.github.cowwoc.requirements13.java.validator.CharacterValidator;
import io.github.cowwoc.requirements13.java.validator.CollectionValidator;
import io.github.cowwoc.requirements13.java.validator.ComparableValidator;
import io.github.cowwoc.requirements13.java.validator.DoubleValidator;
import io.github.cowwoc.requirements13.java.validator.FloatValidator;
import io.github.cowwoc.requirements13.java.validator.GenericTypeValidator;
import io.github.cowwoc.requirements13.java.validator.InetAddressValidator;
import io.github.cowwoc.requirements13.java.validator.IntegerValidator;
import io.github.cowwoc.requirements13.java.validator.ListValidator;
import io.github.cowwoc.requirements13.java.validator.LongValidator;
import io.github.cowwoc.requirements13.java.validator.MapValidator;
import io.github.cowwoc.requirements13.java.validator.ObjectArrayValidator;
import io.github.cowwoc.requirements13.java.validator.ObjectValidator;
import io.github.cowwoc.requirements13.java.validator.OptionalValidator;
import io.github.cowwoc.requirements13.java.validator.PathValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveBooleanArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveBooleanValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveByteArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveByteValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveCharacterArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveCharacterValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveDoubleArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveDoubleValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveFloatArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveFloatValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveIntegerArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveIntegerValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveLongArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveLongValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveShortArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveShortValidator;
import io.github.cowwoc.requirements13.java.validator.ShortValidator;
import io.github.cowwoc.requirements13.java.validator.StringValidator;
import io.github.cowwoc.requirements13.java.validator.UriValidator;
import tools.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;

public final class TestValidatorsImpl implements TestValidators
{
	private final JavaValidatorsImpl javaValidators;
	private final GuavaValidatorsImpl guavaValidators;
	private final JacksonValidatorsImpl jacksonValidators;

	/**
	 * Creates a new instance using the default configuration.
	 *
	 * @param scope the application configuration
	 * @throws NullPointerException if {@code configuration} is null
	 */
	public TestValidatorsImpl(ApplicationScope scope)
	{
		this.javaValidators = new JavaValidatorsImpl(scope, Configuration.DEFAULT);
		this.guavaValidators = new GuavaValidatorsImpl(scope, Configuration.DEFAULT);
		this.jacksonValidators = new JacksonValidatorsImpl(scope, Configuration.DEFAULT);
	}

	/**
	 * Creates a copy of an existing validator factory.
	 *
	 * @param other the factory to copy
	 * @throws NullPointerException if {@code other} is null
	 */
	public TestValidatorsImpl(TestValidatorsImpl other)
	{
		this(other.javaValidators.getScope());
		setConfiguration(other.configuration());
		for (Entry<String, Optional<Object>> entry : other.getContext().entrySet())
			withContext(entry.getValue().orElse(null), entry.getKey());
	}

	/**
	 * Set the configuration used by new validators.
	 *
	 * @param configuration the updated configuration
	 * @throws NullPointerException if {@code configuration} is null
	 */
	private void setConfiguration(Configuration configuration)
	{
		javaValidators.setConfiguration(configuration);
		guavaValidators.setConfiguration(configuration);
		jacksonValidators.setConfiguration(configuration);
	}

	@Override
	public PrimitiveByteValidator requireThat(byte value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public ByteValidator requireThat(Byte value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveShortValidator requireThat(short value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public ShortValidator requireThat(Short value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveIntegerValidator requireThat(int value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public IntegerValidator requireThat(Integer value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveLongValidator requireThat(long value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public LongValidator requireThat(Long value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveFloatValidator requireThat(float value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public FloatValidator requireThat(Float value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveDoubleValidator requireThat(double value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public DoubleValidator requireThat(Double value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveBooleanValidator requireThat(boolean value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public BooleanValidator requireThat(Boolean value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveCharacterValidator requireThat(char value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public CharacterValidator requireThat(Character value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public BigIntegerValidator requireThat(BigInteger value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public BigDecimalValidator requireThat(BigDecimal value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public <T extends Comparable<T>> ComparableValidator<T> requireThat(T value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public <T> ObjectValidator<T> requireThat(T value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public <T extends Collection<E>, E> CollectionValidator<T, E> requireThat(T value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public <T extends List<E>, E> ListValidator<T, E> requireThat(T value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveByteArrayValidator requireThat(byte[] value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveShortArrayValidator requireThat(short[] value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveIntegerArrayValidator requireThat(int[] value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveLongArrayValidator requireThat(long[] value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveFloatArrayValidator requireThat(float[] value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveDoubleArrayValidator requireThat(double[] value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveBooleanArrayValidator requireThat(boolean[] value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveCharacterArrayValidator requireThat(char[] value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public <E> ObjectArrayValidator<E[], E> requireThat(E[] value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public <T extends Map<K, V>, K, V> MapValidator<T, K, V> requireThat(T value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PathValidator requireThat(Path value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public StringValidator requireThat(String value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public UriValidator requireThat(URI value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public <T> GenericTypeValidator<T> requireThat(Class<T> value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public <T> GenericTypeValidator<T> requireThat(GenericType<T> value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public <T> OptionalValidator<T> requireThat(Optional<T> value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public InetAddressValidator requireThat(InetAddress value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public PrimitiveByteValidator that(byte value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveByteValidator that(byte value)
	{
		return javaValidators.that(value);
	}

	@Override
	public ByteValidator that(Byte value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public ByteValidator that(Byte value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveShortValidator that(short value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveShortValidator that(short value)
	{
		return javaValidators.that(value);
	}

	@Override
	public ShortValidator that(Short value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public ShortValidator that(Short value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveIntegerValidator that(int value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveIntegerValidator that(int value)
	{
		return javaValidators.that(value);
	}

	@Override
	public IntegerValidator that(Integer value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public IntegerValidator that(Integer value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveLongValidator that(long value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveLongValidator that(long value)
	{
		return javaValidators.that(value);
	}

	@Override
	public LongValidator that(Long value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public LongValidator that(Long value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveFloatValidator that(float value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveFloatValidator that(float value)
	{
		return javaValidators.that(value);
	}

	@Override
	public FloatValidator that(Float value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public FloatValidator that(Float value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveDoubleValidator that(double value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveDoubleValidator that(double value)
	{
		return javaValidators.that(value);
	}

	@Override
	public DoubleValidator that(Double value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public DoubleValidator that(Double value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveBooleanValidator that(boolean value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveBooleanValidator that(boolean value)
	{
		return javaValidators.that(value);
	}

	@Override
	public BooleanValidator that(Boolean value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public BooleanValidator that(Boolean value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveCharacterValidator that(char value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveCharacterValidator that(char value)
	{
		return javaValidators.that(value);
	}

	@Override
	public CharacterValidator that(Character value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public CharacterValidator that(Character value)
	{
		return javaValidators.that(value);
	}

	@Override
	public BigIntegerValidator that(BigInteger value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public BigIntegerValidator that(BigInteger value)
	{
		return javaValidators.that(value);
	}

	@Override
	public BigDecimalValidator that(BigDecimal value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public BigDecimalValidator that(BigDecimal value)
	{
		return javaValidators.that(value);
	}

	@Override
	public <T extends Comparable<T>> ComparableValidator<T> that(T value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public <T extends Comparable<T>> ComparableValidator<T> that(T value)
	{
		return javaValidators.that(value);
	}

	@Override
	public <T> ObjectValidator<T> that(T value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public <T> ObjectValidator<T> that(T value)
	{
		return javaValidators.that(value);
	}

	@Override
	public <T extends Collection<E>, E> CollectionValidator<T, E> that(T value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public <T extends Collection<E>, E> CollectionValidator<T, E> that(T value)
	{
		return javaValidators.that(value);
	}

	@Override
	public <T extends List<E>, E> ListValidator<T, E> that(T value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public <T extends List<E>, E> ListValidator<T, E> that(T value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveByteArrayValidator that(byte[] value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveByteArrayValidator that(byte[] value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveShortArrayValidator that(short[] value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveShortArrayValidator that(short[] value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveIntegerArrayValidator that(int[] value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveIntegerArrayValidator that(int[] value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveLongArrayValidator that(long[] value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveLongArrayValidator that(long[] value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveFloatArrayValidator that(float[] value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveFloatArrayValidator that(float[] value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveDoubleArrayValidator that(double[] value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveDoubleArrayValidator that(double[] value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveBooleanArrayValidator that(boolean[] value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveBooleanArrayValidator that(boolean[] value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveCharacterArrayValidator that(char[] value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PrimitiveCharacterArrayValidator that(char[] value)
	{
		return javaValidators.that(value);
	}

	@Override
	public <E> ObjectArrayValidator<E[], E> that(E[] value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public <E> ObjectArrayValidator<E[], E> that(E[] value)
	{
		return javaValidators.that(value);
	}

	@Override
	public <T extends Map<K, V>, K, V> MapValidator<T, K, V> that(T value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public <T extends Map<K, V>, K, V> MapValidator<T, K, V> that(T value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PathValidator that(Path value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public PathValidator that(Path value)
	{
		return javaValidators.that(value);
	}

	@Override
	public StringValidator that(String value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public StringValidator that(String value)
	{
		return javaValidators.that(value);
	}

	@Override
	public UriValidator that(URI value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public UriValidator that(URI value)
	{
		return javaValidators.that(value);
	}

	@Override
	public <T> GenericTypeValidator<T> that(Class<T> value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public <T> GenericTypeValidator<T> that(GenericType<T> value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public <T> GenericTypeValidator<T> that(Class<T> value)
	{
		return javaValidators.that(value);
	}

	@Override
	public <T> GenericTypeValidator<T> that(GenericType<T> value)
	{
		return javaValidators.that(value);
	}

	@Override
	public <T> OptionalValidator<T> that(Optional<T> value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public <T> OptionalValidator<T> that(Optional<T> value)
	{
		return javaValidators.that(value);
	}

	@Override
	public InetAddressValidator that(InetAddress value, String name)
	{
		return javaValidators.that(value, name);
	}

	@Override
	public InetAddressValidator that(InetAddress value)
	{
		return javaValidators.that(value);
	}

	@Override
	public PrimitiveByteValidator checkIf(byte value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveByteValidator checkIf(byte value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public ByteValidator checkIf(Byte value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public ByteValidator checkIf(Byte value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveShortValidator checkIf(short value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveShortValidator checkIf(short value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public ShortValidator checkIf(Short value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public ShortValidator checkIf(Short value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveIntegerValidator checkIf(int value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveIntegerValidator checkIf(int value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public IntegerValidator checkIf(Integer value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public IntegerValidator checkIf(Integer value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveLongValidator checkIf(long value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveLongValidator checkIf(long value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public LongValidator checkIf(Long value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public LongValidator checkIf(Long value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveFloatValidator checkIf(float value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveFloatValidator checkIf(float value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public FloatValidator checkIf(Float value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public FloatValidator checkIf(Float value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveDoubleValidator checkIf(double value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveDoubleValidator checkIf(double value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public DoubleValidator checkIf(Double value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public DoubleValidator checkIf(Double value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveBooleanValidator checkIf(boolean value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveBooleanValidator checkIf(boolean value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public BooleanValidator checkIf(Boolean value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public BooleanValidator checkIf(Boolean value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveCharacterValidator checkIf(char value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveCharacterValidator checkIf(char value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public CharacterValidator checkIf(Character value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public CharacterValidator checkIf(Character value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public BigIntegerValidator checkIf(BigInteger value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public BigIntegerValidator checkIf(BigInteger value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public BigDecimalValidator checkIf(BigDecimal value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public BigDecimalValidator checkIf(BigDecimal value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public <T extends Comparable<T>> ComparableValidator<T> checkIf(T value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public <T extends Comparable<T>> ComparableValidator<T> checkIf(T value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public <T> ObjectValidator<T> checkIf(T value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public <T> ObjectValidator<T> checkIf(T value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public <T extends Collection<E>, E> CollectionValidator<T, E> checkIf(T value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public <T extends Collection<E>, E> CollectionValidator<T, E> checkIf(T value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public <T extends List<E>, E> ListValidator<T, E> checkIf(T value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public <T extends List<E>, E> ListValidator<T, E> checkIf(T value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveByteArrayValidator checkIf(byte[] value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveByteArrayValidator checkIf(byte[] value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveShortArrayValidator checkIf(short[] value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveShortArrayValidator checkIf(short[] value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveIntegerArrayValidator checkIf(int[] value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveIntegerArrayValidator checkIf(int[] value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveLongArrayValidator checkIf(long[] value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveLongArrayValidator checkIf(long[] value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveFloatArrayValidator checkIf(float[] value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveFloatArrayValidator checkIf(float[] value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveDoubleArrayValidator checkIf(double[] value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveDoubleArrayValidator checkIf(double[] value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveBooleanArrayValidator checkIf(boolean[] value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveBooleanArrayValidator checkIf(boolean[] value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PrimitiveCharacterArrayValidator checkIf(char[] value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PrimitiveCharacterArrayValidator checkIf(char[] value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public <E> ObjectArrayValidator<E[], E> checkIf(E[] value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public <E> ObjectArrayValidator<E[], E> checkIf(E[] value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public <T extends Map<K, V>, K, V> MapValidator<T, K, V> checkIf(T value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public <T extends Map<K, V>, K, V> MapValidator<T, K, V> checkIf(T value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public PathValidator checkIf(Path value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public PathValidator checkIf(Path value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public StringValidator checkIf(String value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public StringValidator checkIf(String value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public UriValidator checkIf(URI value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public UriValidator checkIf(URI value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public <T> GenericTypeValidator<T> checkIf(Class<T> value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public <T> GenericTypeValidator<T> checkIf(GenericType<T> value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public <T> GenericTypeValidator<T> checkIf(Class<T> value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public <T> GenericTypeValidator<T> checkIf(GenericType<T> value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public <T> OptionalValidator<T> checkIf(Optional<T> value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public <T> OptionalValidator<T> checkIf(Optional<T> value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public InetAddressValidator checkIf(InetAddress value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public InetAddressValidator checkIf(InetAddress value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> requireThat(T value, String name)
	{
		return guavaValidators.requireThat(value, name);
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> assertThat(T value, String name)
	{
		return guavaValidators.assertThat(value, name);
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> assertThat(T value)
	{
		return guavaValidators.assertThat(value);
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> checkIf(T value, String name)
	{
		return guavaValidators.checkIf(value, name);
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> checkIf(T value)
	{
		return guavaValidators.checkIf(value);
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> requireThat(T value, String name)
	{
		return jacksonValidators.requireThat(value, name);
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> assertThat(T value, String name)
	{
		return jacksonValidators.assertThat(value, name);
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> assertThat(T value)
	{
		return jacksonValidators.assertThat(value);
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> checkIf(T value, String name)
	{
		return jacksonValidators.checkIf(value, name);
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> checkIf(T value)
	{
		return jacksonValidators.checkIf(value);
	}

	@Override
	@CheckReturnValue
	public GlobalConfiguration globalConfiguration()
	{
		return javaValidators.globalConfiguration();
	}

	@Override
	public Configuration configuration()
	{
		return javaValidators.configuration();
	}

	@Override
	public ConfigurationUpdater updateConfiguration()
	{
		return javaValidators.updateAndSetConfiguration(this::setConfiguration);
	}

	@Override
	public TestValidators updateConfiguration(
		Consumer<ConfigurationUpdater> consumer)
	{
		try (ConfigurationUpdater updater = updateConfiguration())
		{
			consumer.accept(updater);
		}
		return this;
	}

	@Override
	public TestValidators copy()
	{
		return new TestValidatorsImpl(this);
	}

	@Override
	public Map<String, Optional<Object>> getContext()
	{
		return javaValidators.getContext();
	}

	@Override
	public TestValidators withContext(Object value, String name)
	{
		javaValidators.withContext(value, name);
		guavaValidators.withContext(value, name);
		jacksonValidators.withContext(value, name);
		return this;
	}

	@Override
	public TestValidators removeContext(String name)
	{
		javaValidators.removeContext(name);
		guavaValidators.removeContext(name);
		jacksonValidators.removeContext(name);
		return this;
	}
}