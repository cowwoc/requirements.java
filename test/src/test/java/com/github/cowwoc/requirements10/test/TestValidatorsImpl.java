package com.github.cowwoc.requirements10.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.cowwoc.requirements10.annotation.CheckReturnValue;
import com.github.cowwoc.requirements10.guava.MultimapValidator;
import com.github.cowwoc.requirements10.guava.internal.validator.GuavaValidatorsImpl;
import com.github.cowwoc.requirements10.jackson.JsonNodeValidator;
import com.github.cowwoc.requirements10.jackson.internal.validator.JacksonValidatorsImpl;
import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.GlobalConfiguration;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator;
import com.github.cowwoc.requirements10.java.internal.validator.JavaValidatorsImpl;
import com.github.cowwoc.requirements10.java.validator.BigDecimalValidator;
import com.github.cowwoc.requirements10.java.validator.BigIntegerValidator;
import com.github.cowwoc.requirements10.java.validator.BooleanValidator;
import com.github.cowwoc.requirements10.java.validator.ByteValidator;
import com.github.cowwoc.requirements10.java.validator.CharacterValidator;
import com.github.cowwoc.requirements10.java.validator.ClassValidator;
import com.github.cowwoc.requirements10.java.validator.CollectionValidator;
import com.github.cowwoc.requirements10.java.validator.ComparableValidator;
import com.github.cowwoc.requirements10.java.validator.DoubleValidator;
import com.github.cowwoc.requirements10.java.validator.FloatValidator;
import com.github.cowwoc.requirements10.java.validator.InetAddressValidator;
import com.github.cowwoc.requirements10.java.validator.IntegerValidator;
import com.github.cowwoc.requirements10.java.validator.ListValidator;
import com.github.cowwoc.requirements10.java.validator.LongValidator;
import com.github.cowwoc.requirements10.java.validator.MapValidator;
import com.github.cowwoc.requirements10.java.validator.ObjectArrayValidator;
import com.github.cowwoc.requirements10.java.validator.ObjectValidator;
import com.github.cowwoc.requirements10.java.validator.OptionalValidator;
import com.github.cowwoc.requirements10.java.validator.PathValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveBooleanArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveBooleanValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveByteArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveByteValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveCharacterArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveCharacterValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveDoubleArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveDoubleValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveFloatArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveFloatValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveIntegerArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveIntegerValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveLongArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveLongValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveShortArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveShortValidator;
import com.github.cowwoc.requirements10.java.validator.ShortValidator;
import com.github.cowwoc.requirements10.java.validator.StringValidator;
import com.github.cowwoc.requirements10.java.validator.UriValidator;
import com.github.cowwoc.requirements10.java.validator.UrlValidator;
import com.google.common.collect.Multimap;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
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
		for (Entry<String, Object> entry : other.getContext().entrySet())
			withContext(entry.getValue(), entry.getKey());
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
	public UrlValidator requireThat(URL value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public <T> ClassValidator<T> requireThat(Class<T> value, String name)
	{
		return javaValidators.requireThat(value, name);
	}

	@Override
	public <T> ClassValidator<T> requireThat(GenericType<T> value, String name)
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
	public PrimitiveByteValidator assumeThat(byte value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveByteValidator assumeThat(byte value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public ByteValidator assumeThat(Byte value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public ByteValidator assumeThat(Byte value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveShortValidator assumeThat(short value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveShortValidator assumeThat(short value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public ShortValidator assumeThat(Short value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public ShortValidator assumeThat(Short value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveIntegerValidator assumeThat(int value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveIntegerValidator assumeThat(int value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public IntegerValidator assumeThat(Integer value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public IntegerValidator assumeThat(Integer value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveLongValidator assumeThat(long value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveLongValidator assumeThat(long value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public LongValidator assumeThat(Long value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public LongValidator assumeThat(Long value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveFloatValidator assumeThat(float value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveFloatValidator assumeThat(float value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public FloatValidator assumeThat(Float value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public FloatValidator assumeThat(Float value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveDoubleValidator assumeThat(double value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveDoubleValidator assumeThat(double value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public DoubleValidator assumeThat(Double value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public DoubleValidator assumeThat(Double value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveBooleanValidator assumeThat(boolean value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveBooleanValidator assumeThat(boolean value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public BooleanValidator assumeThat(Boolean value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public BooleanValidator assumeThat(Boolean value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveCharacterValidator assumeThat(char value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveCharacterValidator assumeThat(char value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public CharacterValidator assumeThat(Character value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public CharacterValidator assumeThat(Character value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public BigIntegerValidator assumeThat(BigInteger value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public BigIntegerValidator assumeThat(BigInteger value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public BigDecimalValidator assumeThat(BigDecimal value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public BigDecimalValidator assumeThat(BigDecimal value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public <T extends Comparable<T>> ComparableValidator<T> assumeThat(T value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public <T extends Comparable<T>> ComparableValidator<T> assumeThat(T value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public <T> ObjectValidator<T> assumeThat(T value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public <T> ObjectValidator<T> assumeThat(T value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public <T extends Collection<E>, E> CollectionValidator<T, E> assumeThat(T value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public <T extends Collection<E>, E> CollectionValidator<T, E> assumeThat(T value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public <T extends List<E>, E> ListValidator<T, E> assumeThat(T value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public <T extends List<E>, E> ListValidator<T, E> assumeThat(T value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveByteArrayValidator assumeThat(byte[] value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveByteArrayValidator assumeThat(byte[] value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveShortArrayValidator assumeThat(short[] value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveShortArrayValidator assumeThat(short[] value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveIntegerArrayValidator assumeThat(int[] value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveIntegerArrayValidator assumeThat(int[] value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveLongArrayValidator assumeThat(long[] value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveLongArrayValidator assumeThat(long[] value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveFloatArrayValidator assumeThat(float[] value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveFloatArrayValidator assumeThat(float[] value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveDoubleArrayValidator assumeThat(double[] value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveDoubleArrayValidator assumeThat(double[] value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveBooleanArrayValidator assumeThat(boolean[] value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveBooleanArrayValidator assumeThat(boolean[] value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PrimitiveCharacterArrayValidator assumeThat(char[] value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PrimitiveCharacterArrayValidator assumeThat(char[] value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public <E> ObjectArrayValidator<E[], E> assumeThat(E[] value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public <E> ObjectArrayValidator<E[], E> assumeThat(E[] value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public <T extends Map<K, V>, K, V> MapValidator<T, K, V> assumeThat(T value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public <T extends Map<K, V>, K, V> MapValidator<T, K, V> assumeThat(T value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public PathValidator assumeThat(Path value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public PathValidator assumeThat(Path value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public StringValidator assumeThat(String value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public StringValidator assumeThat(String value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public UriValidator assumeThat(URI value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public UriValidator assumeThat(URI value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public UrlValidator assumeThat(URL value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public UrlValidator assumeThat(URL value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public <T> ClassValidator<T> assumeThat(Class<T> value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public <T> ClassValidator<T> assumeThat(GenericType<T> value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public <T> ClassValidator<T> assumeThat(Class<T> value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public <T> ClassValidator<T> assumeThat(GenericType<T> value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public <T> OptionalValidator<T> assumeThat(Optional<T> value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public <T> OptionalValidator<T> assumeThat(Optional<T> value)
	{
		return javaValidators.assumeThat(value);
	}

	@Override
	public InetAddressValidator assumeThat(InetAddress value, String name)
	{
		return javaValidators.assumeThat(value, name);
	}

	@Override
	public InetAddressValidator assumeThat(InetAddress value)
	{
		return javaValidators.assumeThat(value);
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
	public UrlValidator checkIf(URL value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public UrlValidator checkIf(URL value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public <T> ClassValidator<T> checkIf(Class<T> value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public <T> ClassValidator<T> checkIf(GenericType<T> value, String name)
	{
		return javaValidators.checkIf(value, name);
	}

	@Override
	public <T> ClassValidator<T> checkIf(Class<T> value)
	{
		return javaValidators.checkIf(value);
	}

	@Override
	public <T> ClassValidator<T> checkIf(GenericType<T> value)
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
	public <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> assumeThat(T value, String name)
	{
		return guavaValidators.assumeThat(value, name);
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> assumeThat(T value)
	{
		return guavaValidators.assumeThat(value);
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
	public <T extends JsonNode> JsonNodeValidator<T> assumeThat(T value, String name)
	{
		return jacksonValidators.assumeThat(value, name);
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> assumeThat(T value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> checkIf(T value, String name)
	{
		return jacksonValidators.checkIf(value, name);
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> checkIf(T value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
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
	public TestValidators updateConfiguration(Consumer<ConfigurationUpdater> consumer)
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
	public Map<String, Object> getContext()
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