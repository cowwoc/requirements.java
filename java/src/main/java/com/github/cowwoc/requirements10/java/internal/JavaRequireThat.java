/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal;

import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.validator.BigDecimalValidator;
import com.github.cowwoc.requirements10.java.validator.BigIntegerValidator;
import com.github.cowwoc.requirements10.java.validator.BooleanValidator;
import com.github.cowwoc.requirements10.java.validator.ByteValidator;
import com.github.cowwoc.requirements10.java.validator.CharacterValidator;
import com.github.cowwoc.requirements10.java.validator.CollectionValidator;
import com.github.cowwoc.requirements10.java.validator.ComparableValidator;
import com.github.cowwoc.requirements10.java.validator.DoubleValidator;
import com.github.cowwoc.requirements10.java.validator.FloatValidator;
import com.github.cowwoc.requirements10.java.validator.GenericTypeValidator;
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
 * Creates validators for the Java API that throw exceptions immediately on validation failure.
 */
public interface JavaRequireThat
{
	/**
	 * Validates the state of a {@code byte}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveByteValidator requireThat(byte value, String name);

	/**
	 * Validates the state of a {@code Byte}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	ByteValidator requireThat(Byte value, String name);

	/**
	 * Validates the state of a {@code short}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveShortValidator requireThat(short value, String name);

	/**
	 * Validates the state of a {@code Short}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	ShortValidator requireThat(Short value, String name);

	/**
	 * Validates the state of an {@code int}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveIntegerValidator requireThat(int value, String name);

	/**
	 * Validates the state of an {@code Integer}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	IntegerValidator requireThat(Integer value, String name);

	/**
	 * Validates the state of a {@code long}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveLongValidator requireThat(long value, String name);

	/**
	 * Validates the state of a {@code Long}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	LongValidator requireThat(Long value, String name);

	/**
	 * Validates the state of a {@code float}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveFloatValidator requireThat(float value, String name);

	/**
	 * Validates the state of a {@code Float}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	FloatValidator requireThat(Float value, String name);

	/**
	 * Validates the state of a {@code double}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveDoubleValidator requireThat(double value, String name);

	/**
	 * Validates the state of a {@code Double}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	DoubleValidator requireThat(Double value, String name);

	/**
	 * Validates the state of a {@code boolean}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveBooleanValidator requireThat(boolean value, String name);

	/**
	 * Validates the state of a {@code Boolean}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	BooleanValidator requireThat(Boolean value, String name);

	/**
	 * Validates the state of a {@code char}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveCharacterValidator requireThat(char value, String name);

	/**
	 * Validates the state of a {@code Character}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	CharacterValidator requireThat(Character value, String name);

	/**
	 * Validates the state of a {@code BigInteger}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	BigIntegerValidator requireThat(BigInteger value, String name);

	/**
	 * Validates the state of a {@code BigDecimal}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
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
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
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
	 * Validates the state of an {@code Object}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
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
	 * Validates the state of a {@code Collection}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
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
	 * Validates the state of a {@code List}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
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
	 * Validates the state of a primitive {@code byte} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveByteArrayValidator requireThat(byte[] value, String name);

	/**
	 * Validates the state of a primitive {@code short} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveShortArrayValidator requireThat(short[] value, String name);

	/**
	 * Validates the state of a primitive {@code int} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveIntegerArrayValidator requireThat(int[] value, String name);

	/**
	 * Validates the state of a primitive {@code long} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveLongArrayValidator requireThat(long[] value, String name);

	/**
	 * Validates the state of a primitive {@code float} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveFloatArrayValidator requireThat(float[] value, String name);

	/**
	 * Validates the state of a primitive {@code double} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveDoubleArrayValidator requireThat(double[] value, String name);

	/**
	 * Validates the state of a primitive {@code boolean} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveBooleanArrayValidator requireThat(boolean[] value, String name);

	/**
	 * Validates the state of a primitive {@code char} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveCharacterArrayValidator requireThat(char[] value, String name);

	/**
	 * Validates the state of an {@code Object} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
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
	 * Validates the state of a {@code Map}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
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
	 * Validates the state of a {@code Path}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PathValidator requireThat(Path value, String name);

	/**
	 * Validates the state of a {@code String}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	StringValidator requireThat(String value, String name);

	/**
	 * Validates the state of a {@code URI}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	UriValidator requireThat(URI value, String name);

	/**
	 * Validates the state of a {@code URL}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	UrlValidator requireThat(URL value, String name);

	/**
	 * Validates the state of a {@code Class}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object. For types that contain
	 *              type-parameters, use the {@link #requireThat(GenericType, String) TypeToken} overload.
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T> GenericTypeValidator<T> requireThat(Class<T> value, String name);

	/**
	 * Validates the state of a {@code Class}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object. For types without
	 *              type-parameters, prefer the {@link #requireThat(Class, String) Class} overload.
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T> GenericTypeValidator<T> requireThat(GenericType<T> value, String name);

	/**
	 * Validates the state of an {@code Optional}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
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
	 * Validates the state of an {@code InetAddress}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	InetAddressValidator requireThat(InetAddress value, String name);
}