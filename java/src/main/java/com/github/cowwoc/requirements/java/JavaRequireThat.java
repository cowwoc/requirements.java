/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.validator.BigDecimalValidator;
import com.github.cowwoc.requirements.java.validator.BigIntegerValidator;
import com.github.cowwoc.requirements.java.validator.BooleanValidator;
import com.github.cowwoc.requirements.java.validator.ByteValidator;
import com.github.cowwoc.requirements.java.validator.CharacterValidator;
import com.github.cowwoc.requirements.java.validator.ClassValidator;
import com.github.cowwoc.requirements.java.validator.CollectionValidator;
import com.github.cowwoc.requirements.java.validator.ComparableValidator;
import com.github.cowwoc.requirements.java.validator.DoubleValidator;
import com.github.cowwoc.requirements.java.validator.FloatValidator;
import com.github.cowwoc.requirements.java.validator.InetAddressValidator;
import com.github.cowwoc.requirements.java.validator.IntegerValidator;
import com.github.cowwoc.requirements.java.validator.ListValidator;
import com.github.cowwoc.requirements.java.validator.LongValidator;
import com.github.cowwoc.requirements.java.validator.MapValidator;
import com.github.cowwoc.requirements.java.validator.ObjectArrayValidator;
import com.github.cowwoc.requirements.java.validator.ObjectValidator;
import com.github.cowwoc.requirements.java.validator.OptionalValidator;
import com.github.cowwoc.requirements.java.validator.PathValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveBooleanArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveBooleanValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveByteArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveByteValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveCharacterArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveCharacterValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveDoubleArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveDoubleValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveFloatArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveFloatValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveIntegerArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveLongArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveLongValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveShortArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveShortValidator;
import com.github.cowwoc.requirements.java.validator.ShortValidator;
import com.github.cowwoc.requirements.java.validator.StringValidator;
import com.github.cowwoc.requirements.java.validator.UriValidator;
import com.github.cowwoc.requirements.java.validator.UrlValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveIntegerValidator;

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

/**
 * Creates validators for method preconditions of the Java API, throwing an exception if a failure occurs.
 */
public interface JavaRequireThat
{
	/**
	 * Validates the state of a {@code byte}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveByteValidator requireThat(byte value, String name);

	/**
	 * Validates the state of a {@code Byte}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	ByteValidator requireThat(Byte value, String name);

	/**
	 * Validates the state of a {@code short}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveShortValidator requireThat(short value, String name);

	/**
	 * Validates the state of a {@code Short}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	ShortValidator requireThat(Short value, String name);

	/**
	 * Validates the state of an {@code int}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveIntegerValidator requireThat(int value, String name);

	/**
	 * Validates the state of an {@code Integer}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	IntegerValidator requireThat(Integer value, String name);

	/**
	 * Validates the state of a {@code long}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveLongValidator requireThat(long value, String name);

	/**
	 * Validates the state of a {@code Long}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	LongValidator requireThat(Long value, String name);

	/**
	 * Validates the state of a {@code float}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveFloatValidator requireThat(float value, String name);

	/**
	 * Validates the state of a {@code Float}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	FloatValidator requireThat(Float value, String name);

	/**
	 * Validates the state of a {@code double}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveDoubleValidator requireThat(double value, String name);

	/**
	 * Validates the state of a {@code Double}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	DoubleValidator requireThat(Double value, String name);

	/**
	 * Validates the state of a {@code boolean}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveBooleanValidator requireThat(boolean value, String name);

	/**
	 * Validates the state of a {@code Boolean}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	BooleanValidator requireThat(Boolean value, String name);

	/**
	 * Validates the state of a {@code char}, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveCharacterValidator requireThat(char value, String name);

	/**
	 * Validates the state of a {@code Character}, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	CharacterValidator requireThat(Character value, String name);

	/**
	 * Validates the state of a {@code BigInteger}, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	BigIntegerValidator requireThat(BigInteger value, String name);

	/**
	 * Validates the state of a {@code BigDecimal}, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	BigDecimalValidator requireThat(BigDecimal value, String name);

	/**
	 * Validates the state of a {@code Comparable} object.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T extends Comparable<T>> ComparableValidator<T> requireThat(T value, String name);

	/**
	 * Validates the state of an {@code Object}, throwing an exception on failure.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T> ObjectValidator<T> requireThat(T value, String name);

	/**
	 * Validates the state of a {@code Collection}, throwing an exception on failure.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the collection
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T extends Collection<E>, E> CollectionValidator<T, E> requireThat(T value, String name);

	/**
	 * Validates the state of a {@code List}, throwing an exception on failure.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the list
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T extends List<E>, E> ListValidator<T, E> requireThat(T value, String name);

	/**
	 * Validates the state of a primitive {@code byte} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveByteArrayValidator requireThat(byte[] value, String name);

	/**
	 * Validates the state of a primitive {@code short} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveShortArrayValidator requireThat(short[] value, String name);

	/**
	 * Validates the state of a primitive {@code int} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveIntegerArrayValidator requireThat(int[] value, String name);

	/**
	 * Validates the state of a primitive {@code long} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveLongArrayValidator requireThat(long[] value, String name);

	/**
	 * Validates the state of a primitive {@code float} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveFloatArrayValidator requireThat(float[] value, String name);

	/**
	 * Validates the state of a primitive {@code double} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveDoubleArrayValidator requireThat(double[] value, String name);

	/**
	 * Validates the state of a primitive {@code boolean} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveBooleanArrayValidator requireThat(boolean[] value, String name);

	/**
	 * Validates the state of a primitive {@code char} array, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveCharacterArrayValidator requireThat(char[] value, String name);

	/**
	 * Validates the state of an {@code Object} array, throwing an exception on failure.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<E> ObjectArrayValidator<E[], E> requireThat(E[] value, String name);

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
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T extends Map<K, V>, K, V> MapValidator<T, K, V> requireThat(T value, String name);

	/**
	 * Validates the state of a {@code Path}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PathValidator requireThat(Path value, String name);

	/**
	 * Validates the state of a {@code String}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	StringValidator requireThat(String value, String name);

	/**
	 * Validates the state of a {@code URI}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	UriValidator requireThat(URI value, String name);

	/**
	 * Validates the state of a {@code URL}, throwing an exception on failure
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	UrlValidator requireThat(URL value, String name);

	/**
	 * Validates the state of a {@code Class}, throwing an exception on failure
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object. For types that contain
	 *              type-parameters, use the {@link #requireThat(GenericType, String) TypeToken} overload.
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T> ClassValidator<T> requireThat(Class<T> value, String name);

	/**
	 * Validates the state of a {@code Class}, throwing an exception on failure
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object. For types without
	 *              type-parameters, prefer the {@link #requireThat(Class, String) Class} overload.
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T> ClassValidator<T> requireThat(GenericType<T> value, String name);

	/**
	 * Validates the state of an {@code Optional}, throwing an exception on failure.
	 *
	 * @param <T>   the type of optional
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	<T> OptionalValidator<T> requireThat(Optional<T> value, String name);

	/**
	 * Validates the state of an {@code InetAddress}, throwing an exception on failure.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	InetAddressValidator requireThat(InetAddress value, String name);
}