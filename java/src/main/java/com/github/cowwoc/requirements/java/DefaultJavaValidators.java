/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.java.internal.implementation.JavaValidatorsImpl;
import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;
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
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Creates validators for the Java API, using the default configuration.
 *
 * @see JavaValidators#newInstance() Creating an independent configuration
 */
public final class DefaultJavaValidators
{
	private static final JavaValidatorsImpl delegate = new JavaValidatorsImpl(MainApplicationScope.INSTANCE,
		Configuration.DEFAULT);

	private DefaultJavaValidators()
	{
	}

	/**
	 * Validates the state of a {@code byte}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveByteValidator requireThat(byte value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Byte}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static ByteValidator requireThat(Byte value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code short}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveShortValidator requireThat(short value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Short}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static ShortValidator requireThat(Short value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of an {@code int}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveIntegerValidator requireThat(int value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of an {@code Integer}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static IntegerValidator requireThat(Integer value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code long}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveLongValidator requireThat(long value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Long}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static LongValidator requireThat(Long value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code float}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveFloatValidator requireThat(float value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Float}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static FloatValidator requireThat(Float value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code double}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveDoubleValidator requireThat(double value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Double}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static DoubleValidator requireThat(Double value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code boolean}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveBooleanValidator requireThat(boolean value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Boolean}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static BooleanValidator requireThat(Boolean value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code char}, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveCharacterValidator requireThat(char value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Character}, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static CharacterValidator requireThat(Character value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code BigInteger}, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static BigIntegerValidator requireThat(BigInteger value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code BigDecimal}, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static BigDecimalValidator requireThat(BigDecimal value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Comparable} object.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <T extends Comparable<T>> ComparableValidator<T> requireThat(T value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of an {@code Object}, throwing an exception on failure.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <T> ObjectValidator<T> requireThat(T value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Collection}, throwing an exception on failure.
	 *
	 * @param <E>   the type of elements in the collection
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <E, T extends Collection<E>> CollectionValidator<E, T> requireThat(T value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code List}, throwing an exception on failure.
	 *
	 * @param <E>   the type of elements in the list
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <E, T extends List<E>> ListValidator<E, T> requireThat(T value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code byte} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveByteArrayValidator requireThat(byte[] value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code short} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveShortArrayValidator requireThat(short[] value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code int} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveIntegerArrayValidator requireThat(int[] value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code long} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveLongArrayValidator requireThat(long[] value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code float} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveFloatArrayValidator requireThat(float[] value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code double} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveDoubleArrayValidator requireThat(double[] value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code boolean} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveBooleanArrayValidator requireThat(boolean[] value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code char} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveCharacterArrayValidator requireThat(char[] value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of an {@code Object} array, throwing an exception on failure.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <E> ObjectArrayValidator<E, E[]> requireThat(E[] value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Map}, throwing an exception on failure
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the map
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <K, V, T extends Map<K, V>> MapValidator<K, V, T> requireThat(T value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Path}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PathValidator requireThat(Path value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code String}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static StringValidator requireThat(String value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code URI}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static UriValidator requireThat(URI value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code URL}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static UrlValidator requireThat(URL value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Class}, throwing an exception on failure
	 *
	 * @param <T>   the type of class
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <T> ClassValidator<T> requireThat(Class<T> value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of an {@code Optional}, throwing an exception on failure.
	 *
	 * @param <T>   the type of optional
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static <T> OptionalValidator<T> requireThat(Optional<T> value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of an {@code InetAddress}, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static InetAddressValidator requireThat(InetAddress value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code byte}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveByteValidator assumeThat(byte value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code byte}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveByteValidator assumeThat(byte value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code Byte}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static ByteValidator assumeThat(Byte value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code Byte}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static ByteValidator assumeThat(Byte value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code short}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveShortValidator assumeThat(short value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code short}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveShortValidator assumeThat(short value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code Short}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static ShortValidator assumeThat(Short value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code Short}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static ShortValidator assumeThat(Short value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of an {@code int}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveIntegerValidator assumeThat(int value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of an {@code int}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveIntegerValidator assumeThat(int value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of an {@code Integer}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static IntegerValidator assumeThat(Integer value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of an {@code Integer}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static IntegerValidator assumeThat(Integer value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code long}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveLongValidator assumeThat(long value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code long}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveLongValidator assumeThat(long value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code Long}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static LongValidator assumeThat(Long value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code Long}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static LongValidator assumeThat(Long value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code float}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveFloatValidator assumeThat(float value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code float}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveFloatValidator assumeThat(float value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code Float}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static FloatValidator assumeThat(Float value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code Float}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static FloatValidator assumeThat(Float value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code double}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveDoubleValidator assumeThat(double value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code double}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveDoubleValidator assumeThat(double value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code Double}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static DoubleValidator assumeThat(Double value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code Double}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static DoubleValidator assumeThat(Double value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code boolean}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveBooleanValidator assumeThat(boolean value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code boolean}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveBooleanValidator assumeThat(boolean value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code Boolean}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static BooleanValidator assumeThat(Boolean value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code Boolean}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static BooleanValidator assumeThat(Boolean value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code char}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveCharacterValidator assumeThat(char value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code char}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveCharacterValidator assumeThat(char value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code Character}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static CharacterValidator assumeThat(Character value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code Character}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static CharacterValidator assumeThat(Character value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code BigInteger}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static BigIntegerValidator assumeThat(BigInteger value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code BigInteger}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static BigIntegerValidator assumeThat(BigInteger value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code BigDecimal}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static BigDecimalValidator assumeThat(BigDecimal value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code BigDecimal}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static BigDecimalValidator assumeThat(BigDecimal value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code Comparable} object.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <T extends Comparable<T>> ComparableValidator<T> assumeThat(T value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code Comparable} object.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T extends Comparable<T>> ComparableValidator<T> assumeThat(T value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of an {@code Object}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <T> ObjectValidator<T> assumeThat(T value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of an {@code Object}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of value
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T> ObjectValidator<T> assumeThat(T value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code Collection}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <E>   the type of elements in the collection
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <E, T extends Collection<E>> CollectionValidator<E, T> assumeThat(T value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code Collection}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <E>   the type of elements in the collection
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <E, T extends Collection<E>> CollectionValidator<E, T> assumeThat(T value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code List}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <E>   the type of elements in the list
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <E, T extends List<E>> ListValidator<E, T> assumeThat(T value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code List}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <E>   the type of elements in the list
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <E, T extends List<E>> ListValidator<E, T> assumeThat(T value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a primitive {@code byte} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveByteArrayValidator assumeThat(byte[] value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code byte} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveByteArrayValidator assumeThat(byte[] value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a primitive {@code short} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveShortArrayValidator assumeThat(short[] value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code short} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveShortArrayValidator assumeThat(short[] value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a primitive {@code int} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveIntegerArrayValidator assumeThat(int[] value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code int} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveIntegerArrayValidator assumeThat(int[] value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a primitive {@code long} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveLongArrayValidator assumeThat(long[] value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code long} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveLongArrayValidator assumeThat(long[] value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a primitive {@code float} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveFloatArrayValidator assumeThat(float[] value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code float} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveFloatArrayValidator assumeThat(float[] value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a primitive {@code double} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveDoubleArrayValidator assumeThat(double[] value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code double} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveDoubleArrayValidator assumeThat(double[] value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a primitive {@code boolean} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveBooleanArrayValidator assumeThat(boolean[] value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code boolean} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveBooleanArrayValidator assumeThat(boolean[] value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a primitive {@code char} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveCharacterArrayValidator assumeThat(char[] value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a primitive {@code char} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveCharacterArrayValidator assumeThat(char[] value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of an {@code Object} array.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <E> ObjectArrayValidator<E, E[]> assumeThat(E[] value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of an {@code Object} array.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <E> ObjectArrayValidator<E, E[]> assumeThat(E[] value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code Map}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the map
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <K, V, T extends Map<K, V>> MapValidator<K, V, T> assumeThat(T value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code Map}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the map
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <K, V, T extends Map<K, V>> MapValidator<K, V, T> assumeThat(T value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code Path}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PathValidator assumeThat(Path value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code Path}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PathValidator assumeThat(Path value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code String}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static StringValidator assumeThat(String value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code String}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static StringValidator assumeThat(String value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code URI}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static UriValidator assumeThat(URI value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code URI}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static UriValidator assumeThat(URI value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code URL}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static UrlValidator assumeThat(URL value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code URL}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static UrlValidator assumeThat(URL value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code Class}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of class
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <T> ClassValidator<T> assumeThat(Class<T> value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code Class}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of class
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T> ClassValidator<T> assumeThat(Class<T> value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of an {@code Optional}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of optional
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static <T> OptionalValidator<T> assumeThat(Optional<T> value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of an {@code Optional}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of optional
	 * @param value the value
	 * @return a validator for the value
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static <T> OptionalValidator<T> assumeThat(Optional<T> value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of an {@code InetAddress}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static InetAddressValidator assumeThat(InetAddress value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of an {@code InetAddress}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static InetAddressValidator assumeThat(InetAddress value)
	{
		return delegate.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code byte}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveByteValidator checkIf(byte value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code byte}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveByteValidator checkIf(byte value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Byte}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static ByteValidator checkIf(Byte value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Byte}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static ByteValidator checkIf(Byte value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code short}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveShortValidator checkIf(short value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code short}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveShortValidator checkIf(short value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Short}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static ShortValidator checkIf(Short value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Short}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static ShortValidator checkIf(Short value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of an {@code int}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveIntegerValidator checkIf(int value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of an {@code int}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveIntegerValidator checkIf(int value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of an {@code Integer}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static IntegerValidator checkIf(Integer value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of an {@code Integer}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static IntegerValidator checkIf(Integer value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code long}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveLongValidator checkIf(long value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code long}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveLongValidator checkIf(long value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Long}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static LongValidator checkIf(Long value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Long}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static LongValidator checkIf(Long value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code float}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveFloatValidator checkIf(float value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code float}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveFloatValidator checkIf(float value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Float}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static FloatValidator checkIf(Float value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Float}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static FloatValidator checkIf(Float value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code double}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveDoubleValidator checkIf(double value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code double}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveDoubleValidator checkIf(double value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Double}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static DoubleValidator checkIf(Double value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Double}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static DoubleValidator checkIf(Double value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code boolean}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveBooleanValidator checkIf(boolean value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code boolean}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveBooleanValidator checkIf(boolean value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Boolean}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static BooleanValidator checkIf(Boolean value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Boolean}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static BooleanValidator checkIf(Boolean value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code char}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveCharacterValidator checkIf(char value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code char}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveCharacterValidator checkIf(char value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Character}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static CharacterValidator checkIf(Character value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Character}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static CharacterValidator checkIf(Character value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code BigInteger}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static BigIntegerValidator checkIf(BigInteger value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code BigInteger}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static BigIntegerValidator checkIf(BigInteger value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code BigDecimal}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static BigDecimalValidator checkIf(BigDecimal value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code BigDecimal}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static BigDecimalValidator checkIf(BigDecimal value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Comparable} object.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <T extends Comparable<T>> ComparableValidator<T> checkIf(T value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Comparable} object.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T extends Comparable<T>> ComparableValidator<T> checkIf(T value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of an {@code Object}, recording any failures.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <T> ObjectValidator<T> checkIf(T value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of an {@code Object}, recording any failures.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T> ObjectValidator<T> checkIf(T value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Collection}, recording any failures.
	 *
	 * @param <E>   the type of elements in the collection
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <E, T extends Collection<E>> CollectionValidator<E, T> checkIf(T value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Collection}, recording any failures.
	 *
	 * @param <E>   the type of elements in the collection
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <E, T extends Collection<E>> CollectionValidator<E, T> checkIf(T value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code List}, recording any failures.
	 *
	 * @param <E>   the type of elements in the list
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <E, T extends List<E>> ListValidator<E, T> checkIf(T value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code List}, recording any failures.
	 *
	 * @param <E>   the type of elements in the list
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <E, T extends List<E>> ListValidator<E, T> checkIf(T value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code byte} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveByteArrayValidator checkIf(byte[] value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code byte} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveByteArrayValidator checkIf(byte[] value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code short} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveShortArrayValidator checkIf(short[] value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code short} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveShortArrayValidator checkIf(short[] value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code int} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveIntegerArrayValidator checkIf(int[] value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code int} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveIntegerArrayValidator checkIf(int[] value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code long} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveLongArrayValidator checkIf(long[] value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code long} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveLongArrayValidator checkIf(long[] value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code float} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveFloatArrayValidator checkIf(float[] value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code float} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveFloatArrayValidator checkIf(float[] value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code double} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveDoubleArrayValidator checkIf(double[] value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code double} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveDoubleArrayValidator checkIf(double[] value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code boolean} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveBooleanArrayValidator checkIf(boolean[] value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code boolean} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveBooleanArrayValidator checkIf(boolean[] value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code char} array, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PrimitiveCharacterArrayValidator checkIf(char[] value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code char} array, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveCharacterArrayValidator checkIf(char[] value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of an {@code Object} array, recording failures without throwing an exception.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <E> ObjectArrayValidator<E, E[]> checkIf(E[] value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of an {@code Object} array, recording failures without throwing an exception.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <E> ObjectArrayValidator<E, E[]> checkIf(E[] value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Map}, recording failures without throwing an exception.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the map
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <K, V, T extends Map<K, V>> MapValidator<K, V, T> checkIf(T value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Map}, recording failures without throwing an exception.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the map
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <K, V, T extends Map<K, V>> MapValidator<K, V, T> checkIf(T value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Path}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static PathValidator checkIf(Path value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Path}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PathValidator checkIf(Path value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code String}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static StringValidator checkIf(String value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code String}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static StringValidator checkIf(String value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code URI}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static UriValidator checkIf(URI value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code URI}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static UriValidator checkIf(URI value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code URL}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static UrlValidator checkIf(URL value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code URL}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static UrlValidator checkIf(URL value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Class}, recording failures without throwing an exception.
	 *
	 * @param <T>   the type of the class
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <T> ClassValidator<T> checkIf(Class<T> value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Class}, recording failures without throwing an exception.
	 *
	 * @param <T>   the type of the class
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T> ClassValidator<T> checkIf(Class<T> value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of an {@code Optional}, recording failures without throwing an exception.
	 *
	 * @param <T>   the type of the class
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static <T> OptionalValidator<T> checkIf(Optional<T> value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of an {@code Optional}, recording failures without throwing an exception.
	 *
	 * @param <T>   the type of the class
	 * @param value the value
	 * @return a validator for the value
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static <T> OptionalValidator<T> checkIf(Optional<T> value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Validates the state of an {@code InetAddress}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static InetAddressValidator checkIf(InetAddress value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of an {@code InetAddress}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static InetAddressValidator checkIf(InetAddress value)
	{
		return delegate.checkIf(value);
	}

	/**
	 * Returns the configuration used by new validators.
	 *
	 * @return the configuration used by new validators
	 * @see JavaValidators#newInstance() Creating an independent configuration
	 */
	@CheckReturnValue
	public static Configuration configuration()
	{
		return delegate.configuration();
	}

	/**
	 * Updates the configuration used by new validators.
	 * <p>
	 * <b>NOTE</b>: Changes are only applied when {@link ConfigurationUpdater#close()} is invoked.
	 *
	 * @return the configuration updater
	 * @see JavaValidators#newInstance() Creating an independent configuration
	 */
	@CheckReturnValue
	public static ConfigurationUpdater updateConfiguration()
	{
		return delegate.updateConfiguration();
	}

	/**
	 * Returns the contextual information for validations performed by this thread using any validator. The
	 * contextual information is a map of key-value pairs that can provide more details about validation
	 * failures. For example, if the message is "Password may not be empty" and the map contains the key-value
	 * pair {@code {"username": "john.smith"}}, the exception message would be:
	 * <p>
	 * {@snippet lang = output:
	 * Password may not be empty
	 * username: john.smith}
	 * <p>
	 * Values set by this method may be overridden by {@link Validator#context()}.
	 * <p>
	 * <b>NOTE</b>: This method affects existing and new validators used by current thread. Changes are
	 * reversed once {@link ScopedContext#close()} is invoked.
	 *
	 * @return the thread context updater
	 */
	@CheckReturnValue
	public static ScopedContext threadContext()
	{
		return delegate.threadContext();
	}

	/**
	 * Returns the global configuration shared by all validators.
	 * <p>
	 * <b>NOTE</b>: Updating This method affects existing and new validators.
	 *
	 * @return the global configuration updater
	 */
	@CheckReturnValue
	public static GlobalConfiguration globalConfiguration()
	{
		return delegate.globalConfiguration();
	}
}