package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaValidators;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.type.BigDecimalValidator;
import com.github.cowwoc.requirements.java.type.BigIntegerValidator;
import com.github.cowwoc.requirements.java.type.BooleanValidator;
import com.github.cowwoc.requirements.java.type.ByteValidator;
import com.github.cowwoc.requirements.java.type.CharacterValidator;
import com.github.cowwoc.requirements.java.type.ClassValidator;
import com.github.cowwoc.requirements.java.type.CollectionValidator;
import com.github.cowwoc.requirements.java.type.ComparableValidator;
import com.github.cowwoc.requirements.java.type.DoubleValidator;
import com.github.cowwoc.requirements.java.type.FloatValidator;
import com.github.cowwoc.requirements.java.type.InetAddressValidator;
import com.github.cowwoc.requirements.java.type.IntegerValidator;
import com.github.cowwoc.requirements.java.type.ListValidator;
import com.github.cowwoc.requirements.java.type.LongValidator;
import com.github.cowwoc.requirements.java.type.MapValidator;
import com.github.cowwoc.requirements.java.type.ObjectArrayValidator;
import com.github.cowwoc.requirements.java.type.ObjectValidator;
import com.github.cowwoc.requirements.java.type.OptionalValidator;
import com.github.cowwoc.requirements.java.type.PathValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveBooleanArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveBooleanValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveByteArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveByteValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveCharacterArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveCharacterValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveDoubleArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveDoubleValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveFloatArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveFloatValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveIntegerArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveIntegerValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveLongArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveLongValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveShortArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveShortValidator;
import com.github.cowwoc.requirements.java.type.ShortValidator;
import com.github.cowwoc.requirements.java.type.StringValidator;
import com.github.cowwoc.requirements.java.type.UriValidator;
import com.github.cowwoc.requirements.java.type.UrlValidator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The default implementation of JavaValidators.
 */
public final class JavaValidatorsImpl extends AbstractValidators<JavaValidators>
	implements JavaValidators
{
	/**
	 * Creates a new instance of this validator with an independent configuration.
	 *
	 * @param scope         the application configuration
	 * @param configuration the configuration to use for new validators
	 * @throws NullPointerException if any of the arguments are null
	 */
	public JavaValidatorsImpl(ApplicationScope scope, Configuration configuration)
	{
		super(scope, configuration);
	}

	/**
	 * Creates a copy of an existing validator factory.
	 *
	 * @param other the factory to copy
	 * @throws NullPointerException if {@code other} is null
	 */
	public JavaValidatorsImpl(JavaValidatorsImpl other)
	{
		this(other.scope, other.configuration());
		this.context.putAll(other.context);
	}

	@Override
	public PrimitiveByteValidator requireThat(byte value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public ByteValidator requireThat(Byte value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveShortValidator requireThat(short value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public ShortValidator requireThat(Short value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveIntegerValidator requireThat(int value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public IntegerValidator requireThat(Integer value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveLongValidator requireThat(long value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public LongValidator requireThat(Long value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveFloatValidator requireThat(float value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public FloatValidator requireThat(Float value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveDoubleValidator requireThat(double value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public DoubleValidator requireThat(Double value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveBooleanValidator requireThat(boolean value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public BooleanValidator requireThat(Boolean value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveCharacterValidator requireThat(char value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public CharacterValidator requireThat(Character value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public BigIntegerValidator requireThat(BigInteger value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public BigDecimalValidator requireThat(BigDecimal value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public <T extends Comparable<T>> ComparableValidator<T> requireThat(T value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public <T> ObjectValidator<T> requireThat(T value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public <E, T extends Collection<E>> CollectionValidator<E, T> requireThat(T value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public <E, T extends List<E>> ListValidator<E, T> requireThat(T value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveByteArrayValidator requireThat(byte[] value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveShortArrayValidator requireThat(short[] value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveIntegerArrayValidator requireThat(int[] value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveLongArrayValidator requireThat(long[] value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveFloatArrayValidator requireThat(float[] value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveDoubleArrayValidator requireThat(double[] value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveBooleanArrayValidator requireThat(boolean[] value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveCharacterArrayValidator requireThat(char[] value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public <E> ObjectArrayValidator<E, E[]> requireThat(E[] value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public <K, V, T extends Map<K, V>> MapValidator<K, V, T> requireThat(T value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PathValidator requireThat(Path value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public StringValidator requireThat(String value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public UriValidator requireThat(URI value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public UrlValidator requireThat(URL value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public <T> ClassValidator<T> requireThat(Class<T> value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public <T> OptionalValidator<T> requireThat(Optional<T> value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public InetAddressValidator requireThat(InetAddress value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public PrimitiveByteValidator assumeThat(byte value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveByteValidator assumeThat(byte value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public ByteValidator assumeThat(Byte value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public ByteValidator assumeThat(Byte value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveShortValidator assumeThat(short value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveShortValidator assumeThat(short value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public ShortValidator assumeThat(Short value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public ShortValidator assumeThat(Short value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveIntegerValidator assumeThat(int value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveIntegerValidator assumeThat(int value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public IntegerValidator assumeThat(Integer value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public IntegerValidator assumeThat(Integer value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveLongValidator assumeThat(long value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveLongValidator assumeThat(long value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public LongValidator assumeThat(Long value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public LongValidator assumeThat(Long value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveFloatValidator assumeThat(float value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveFloatValidator assumeThat(float value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public FloatValidator assumeThat(Float value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public FloatValidator assumeThat(Float value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveDoubleValidator assumeThat(double value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveDoubleValidator assumeThat(double value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public DoubleValidator assumeThat(Double value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public DoubleValidator assumeThat(Double value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveBooleanValidator assumeThat(boolean value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveBooleanValidator assumeThat(boolean value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public BooleanValidator assumeThat(Boolean value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public BooleanValidator assumeThat(Boolean value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveCharacterValidator assumeThat(char value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveCharacterValidator assumeThat(char value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public CharacterValidator assumeThat(Character value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public CharacterValidator assumeThat(Character value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public BigIntegerValidator assumeThat(BigInteger value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public BigIntegerValidator assumeThat(BigInteger value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public BigDecimalValidator assumeThat(BigDecimal value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public BigDecimalValidator assumeThat(BigDecimal value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <T extends Comparable<T>> ComparableValidator<T> assumeThat(T value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public <T extends Comparable<T>> ComparableValidator<T> assumeThat(T value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <T> ObjectValidator<T> assumeThat(T value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public <T> ObjectValidator<T> assumeThat(T value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <E, T extends Collection<E>> CollectionValidator<E, T> assumeThat(T value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public <E, T extends Collection<E>> CollectionValidator<E, T> assumeThat(T value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <E, T extends List<E>> ListValidator<E, T> assumeThat(T value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public <E, T extends List<E>> ListValidator<E, T> assumeThat(T value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveByteArrayValidator assumeThat(byte[] value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveByteArrayValidator assumeThat(byte[] value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveShortArrayValidator assumeThat(short[] value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveShortArrayValidator assumeThat(short[] value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveIntegerArrayValidator assumeThat(int[] value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveIntegerArrayValidator assumeThat(int[] value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveLongArrayValidator assumeThat(long[] value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveLongArrayValidator assumeThat(long[] value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveFloatArrayValidator assumeThat(float[] value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveFloatArrayValidator assumeThat(float[] value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveDoubleArrayValidator assumeThat(double[] value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveDoubleArrayValidator assumeThat(double[] value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveBooleanArrayValidator assumeThat(boolean[] value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveBooleanArrayValidator assumeThat(boolean[] value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveCharacterArrayValidator assumeThat(char[] value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PrimitiveCharacterArrayValidator assumeThat(char[] value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <E> ObjectArrayValidator<E, E[]> assumeThat(E[] value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public <E> ObjectArrayValidator<E, E[]> assumeThat(E[] value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <K, V, T extends Map<K, V>> MapValidator<K, V, T> assumeThat(T value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public <K, V, T extends Map<K, V>> MapValidator<K, V, T> assumeThat(T value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PathValidator assumeThat(Path value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public PathValidator assumeThat(Path value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public StringValidator assumeThat(String value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public StringValidator assumeThat(String value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public UriValidator assumeThat(URI value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public UriValidator assumeThat(URI value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public UrlValidator assumeThat(URL value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public UrlValidator assumeThat(URL value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <T> ClassValidator<T> assumeThat(Class<T> value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public <T> ClassValidator<T> assumeThat(Class<T> value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <T> OptionalValidator<T> assumeThat(Optional<T> value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public <T> OptionalValidator<T> assumeThat(Optional<T> value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public InetAddressValidator assumeThat(InetAddress value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public InetAddressValidator assumeThat(InetAddress value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveByteValidator checkIf(byte value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveByteValidator checkIf(byte value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public ByteValidator checkIf(Byte value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public ByteValidator checkIf(Byte value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveShortValidator checkIf(short value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveShortValidator checkIf(short value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public ShortValidator checkIf(Short value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public ShortValidator checkIf(Short value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveIntegerValidator checkIf(int value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveIntegerValidator checkIf(int value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public IntegerValidator checkIf(Integer value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public IntegerValidator checkIf(Integer value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveLongValidator checkIf(long value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveLongValidator checkIf(long value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public LongValidator checkIf(Long value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public LongValidator checkIf(Long value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveFloatValidator checkIf(float value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveFloatValidator checkIf(float value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public FloatValidator checkIf(Float value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public FloatValidator checkIf(Float value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveDoubleValidator checkIf(double value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveDoubleValidator checkIf(double value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public DoubleValidator checkIf(Double value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public DoubleValidator checkIf(Double value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveBooleanValidator checkIf(boolean value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveBooleanValidator checkIf(boolean value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public BooleanValidator checkIf(Boolean value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public BooleanValidator checkIf(Boolean value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveCharacterValidator checkIf(char value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveCharacterValidator checkIf(char value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public CharacterValidator checkIf(Character value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public CharacterValidator checkIf(Character value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public BigIntegerValidator checkIf(BigInteger value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public BigIntegerValidator checkIf(BigInteger value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public BigDecimalValidator checkIf(BigDecimal value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public BigDecimalValidator checkIf(BigDecimal value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <T extends Comparable<T>> ComparableValidator<T> checkIf(T value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public <T extends Comparable<T>> ComparableValidator<T> checkIf(T value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <T> ObjectValidator<T> checkIf(T value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public <T> ObjectValidator<T> checkIf(T value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <E, T extends Collection<E>> CollectionValidator<E, T> checkIf(T value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public <E, T extends Collection<E>> CollectionValidator<E, T> checkIf(T value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <E, T extends List<E>> ListValidator<E, T> checkIf(T value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public <E, T extends List<E>> ListValidator<E, T> checkIf(T value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveByteArrayValidator checkIf(byte[] value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveByteArrayValidator checkIf(byte[] value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveShortArrayValidator checkIf(short[] value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveShortArrayValidator checkIf(short[] value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveIntegerArrayValidator checkIf(int[] value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveIntegerArrayValidator checkIf(int[] value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveLongArrayValidator checkIf(long[] value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveLongArrayValidator checkIf(long[] value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveFloatArrayValidator checkIf(float[] value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveFloatArrayValidator checkIf(float[] value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveDoubleArrayValidator checkIf(double[] value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveDoubleArrayValidator checkIf(double[] value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveBooleanArrayValidator checkIf(boolean[] value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveBooleanArrayValidator checkIf(boolean[] value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PrimitiveCharacterArrayValidator checkIf(char[] value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PrimitiveCharacterArrayValidator checkIf(char[] value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <E> ObjectArrayValidator<E, E[]> checkIf(E[] value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public <E> ObjectArrayValidator<E, E[]> checkIf(E[] value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <K, V, T extends Map<K, V>> MapValidator<K, V, T> checkIf(T value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public <K, V, T extends Map<K, V>> MapValidator<K, V, T> checkIf(T value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public PathValidator checkIf(Path value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public PathValidator checkIf(Path value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public StringValidator checkIf(String value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public StringValidator checkIf(String value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public UriValidator checkIf(URI value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public UriValidator checkIf(URI value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public UrlValidator checkIf(URL value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public UrlValidator checkIf(URL value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <T> ClassValidator<T> checkIf(Class<T> value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public <T> ClassValidator<T> checkIf(Class<T> value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <T> OptionalValidator<T> checkIf(Optional<T> value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public <T> OptionalValidator<T> checkIf(Optional<T> value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public InetAddressValidator checkIf(InetAddress value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public InetAddressValidator checkIf(InetAddress value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	private PrimitiveByteValidator newInstance(byte value, String name, Configuration configuration)
	{
		return new PrimitiveByteValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private Map<String, Object> newValidatorContext()
	{
		Map<String, Object> context = HashMap.newHashMap(this.context.size() + 2);
		context.putAll(this.context);
		return context;
	}

	private List<ValidationFailure> newValidatorFailures()
	{
		return new ArrayList<>(2);
	}

	private ByteValidator newInstance(Byte value, String name, Configuration configuration)
	{
		return new ByteValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveShortValidator newInstance(short value, String name, Configuration configuration)
	{
		return new PrimitiveShortValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private ShortValidator newInstance(Short value, String name, Configuration configuration)
	{
		return new ShortValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveIntegerValidator newInstance(int value, String name, Configuration configuration)
	{
		return new PrimitiveIntegerValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private IntegerValidator newInstance(Integer value, String name, Configuration configuration)
	{
		return new IntegerValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveLongValidator newInstance(long value, String name, Configuration configuration)
	{
		return new PrimitiveLongValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private LongValidator newInstance(Long value, String name, Configuration configuration)
	{
		return new LongValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveFloatValidator newInstance(float value, String name, Configuration configuration)
	{
		return new PrimitiveFloatValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private FloatValidator newInstance(Float value, String name, Configuration configuration)
	{
		return new FloatValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveDoubleValidator newInstance(double value, String name, Configuration configuration)
	{
		return new PrimitiveDoubleValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private DoubleValidator newInstance(Double value, String name, Configuration configuration)
	{
		return new DoubleValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveBooleanValidator newInstance(boolean value, String name, Configuration configuration)
	{
		return new PrimitiveBooleanValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private BooleanValidator newInstance(Boolean value, String name, Configuration configuration)
	{
		return new BooleanValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveCharacterValidator newInstance(char value, String name, Configuration configuration)
	{
		return new PrimitiveCharacterValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private CharacterValidator newInstance(Character value, String name, Configuration configuration)
	{
		return new CharacterValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private BigIntegerValidator newInstance(BigInteger value, String name, Configuration configuration)
	{
		return new BigIntegerValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private BigDecimalValidator newInstance(BigDecimal value, String name, Configuration configuration)
	{
		return new BigDecimalValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private <T extends Comparable<T>> ComparableValidator<T> newInstance(T value, String name,
		Configuration configuration)
	{
		return new ComparableValidatorImpl<>(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private <T> ObjectValidator<T> newInstance(T value, String name, Configuration configuration)
	{
		return new ObjectValidatorImpl<>(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private <E, T extends Collection<E>> CollectionValidator<E, T> newInstance(T value, String name,
		Configuration configuration)
	{
		return new CollectionValidatorImpl<>(scope, configuration, name, value, Pluralizer.ELEMENT,
			newValidatorContext(), newValidatorFailures());
	}

	private <E, T extends List<E>> ListValidator<E, T> newInstance(T value, String name,
		Configuration configuration)
	{
		return new ListValidatorImpl<>(scope, configuration, name, value, Pluralizer.ELEMENT,
			newValidatorContext(), newValidatorFailures());
	}

	private PrimitiveByteArrayValidator newInstance(byte[] value, String name, Configuration configuration)
	{
		return new PrimitiveByteArrayValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveShortArrayValidator newInstance(short[] value, String name, Configuration configuration)
	{
		return new PrimitiveShortArrayValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveIntegerArrayValidator newInstance(int[] value, String name, Configuration configuration)
	{
		return new PrimitiveIntegerArrayValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveLongArrayValidator newInstance(long[] value, String name, Configuration configuration)
	{
		return new PrimitiveLongArrayValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveFloatArrayValidator newInstance(float[] value, String name, Configuration configuration)
	{
		return new PrimitiveFloatArrayValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveDoubleArrayValidator newInstance(double[] value, String name, Configuration configuration)
	{
		return new PrimitiveDoubleArrayValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveBooleanArrayValidator newInstance(boolean[] value, String name,
		Configuration configuration)
	{
		return new PrimitiveBooleanArrayValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PrimitiveCharacterArrayValidator newInstance(char[] value, String name, Configuration configuration)
	{
		return new PrimitiveCharacterArrayValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private <E> ObjectArrayValidator<E, E[]> newInstance(E[] value, String name, Configuration configuration)
	{
		return new ObjectArrayValidatorImpl<>(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private <K, V, T extends Map<K, V>> MapValidator<K, V, T> newInstance(T value, String name,
		Configuration configuration)
	{
		return new MapValidatorImpl<>(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private PathValidator newInstance(Path value, String name, Configuration configuration)
	{
		return new PathValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private StringValidator newInstance(String value, String name, Configuration configuration)
	{
		return new StringValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private UriValidator newInstance(URI value, String name, Configuration configuration)
	{
		return new UriValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private UrlValidator newInstance(URL value, String name, Configuration configuration)
	{
		return new UrlValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private <T> ClassValidator<T> newInstance(Class<T> value, String name, Configuration configuration)
	{
		return new ClassValidatorImpl<>(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	private <T> OptionalValidator<T> newInstance(Optional<T> value, String name, Configuration configuration)
	{
		return new OptionalValidatorImpl<>(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private InetAddressValidator newInstance(InetAddress value, String name, Configuration configuration)
	{
		return new InetAddressValidatorImpl(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	@Override
	public JavaValidatorsImpl copy()
	{
		return new JavaValidatorsImpl(this);
	}

	@Override
	public JavaValidatorsImpl putContext(Object value, String name)
	{
		context.put(name, value);
		return this;
	}

	@Override
	public JavaValidatorsImpl removeContext(String name)
	{
		context.remove(name);
		return this;
	}
}